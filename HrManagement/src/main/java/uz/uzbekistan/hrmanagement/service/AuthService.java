package uz.uzbekistan.hrmanagement.service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.uzbekistan.hrmanagement.entity.Role;
import uz.uzbekistan.hrmanagement.entity.User;
import uz.uzbekistan.hrmanagement.entity.api.UserResponse;
import uz.uzbekistan.hrmanagement.entity.payload.LoginDto;
import uz.uzbekistan.hrmanagement.entity.payload.RegisterDto;
import uz.uzbekistan.hrmanagement.jwt.JWTProvider;
import uz.uzbekistan.hrmanagement.repository.RoleRepository;
import uz.uzbekistan.hrmanagement.repository.UserRepository;

import javax.management.relation.RoleList;
import java.util.HashSet;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;


@Service
public class AuthService implements UserDetailsService {
    private final SendMailService mailService;
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(SendMailService mailService, UserRepository repository, RoleRepository roleRepository, @Lazy AuthenticationManager authenticationManager, JWTProvider jwtProvider, @Lazy PasswordEncoder passwordEncoder) {
        this.mailService = mailService;
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findUserByEmail(username).orElse(null);
    }

    //    ROLE_DIRECTOR, ROLE_MANAGER
    public ResponseEntity<?> getUsers(Integer page, Integer size) {
        Page<User> users = repository.findAll(PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10));
        return ok(users.get().map(UserResponse::new).collect(Collectors.toSet()));
    }

    //    ROLE_DIRECTOR, ROLE_MANAGER
    public ResponseEntity<?> getUser(UUID id) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) return ok(new UserResponse(optionalUser.get()));
        return status(HttpStatus.NOT_FOUND).body("User not found");
    }

    public ResponseEntity<?> saveUser(RegisterDto registerDto, HttpServletRequest request) {
        if (repository.existsByEmail(registerDto.getEmail()))
            return status(HttpStatus.CONFLICT).body("Email has already been registered");
        Claims claims = jwtProvider.getClaimsObjectFromToken(getToken(request));
        Optional<User> optionalUser = repository.findUserByEmailAndId(claims.getSubject(), UUID.fromString(claims.getId()));
        if (!optionalUser.isPresent())
            return status(401).body("There was a problem with your access rights. Please log in again :)");
        Set<Role> roles = checkRole(optionalUser.get(), registerDto);
        if (roles.size() == 0) return badRequest().body("The role was entered incorrectly");
        User user = new User(registerDto.getFirstName(), registerDto.getLastName(), registerDto.getEmail(), passwordEncoder.encode(dto.getPassword()), roles);
        String emailCode = UUID.randomUUID().toString();
        user.setEmailCode(emailCode);
        boolean email = mailService.sendEmail(
                registerDto.getEmail(), "Link to activate your email",
                String.format("http://localhost:8080/api/auth/verify?email=%s&emailCode=%s",
                        registerDto.getEmail(), emailCode));
        if (!email) return badRequest().build();
        return status(HttpStatus.CREATED).body(new UserResponse(repository.save(repository.save(user))));
    }

    public boolean checkRole(User user, RoleList role) {
        for (Role userRole : user.getRoles()) {
            if (userRole.getRoleName().equals(role)) return true;
        }
        return false;
    }

    public Set<Role> checkRole(User user, RegisterDto dto) {
        Set<Role> roles = new HashSet<>();
        if (checkRole(user, ROlE_DIRECTOR)) {
            for (Integer roleId : dto.getRole()) {
                Optional<Role> optionalRole = roleRepository.findById(roleId);
                optionalRole.ifPresent(role -> {
                    if (role.getRole() != ROLE_DIRECTOR) roles.add(role);
                });
            }
        } else if (checkRole(user, ROLE_MANAGER)) {
            for (Integer roleId : dto.getRole()) {
                Optional<Role> optionalRole = roleRepository.findById(roleId);
                optionalRole.ifPresent(role -> {
                    if (role.getRole() != ROLE_DIRECTOR && role.getRole() != ROLE_MANAGER) roles.add(role);
                });
            }
        }
        return roles;
    }

    //    FOR AUTH
    public ResponseEntity<?> login(LoginDto dto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
            return ok(jwtProvider.generateToken(authenticate));
        } catch (BadCredentialsException e) {
            return status(401).body("Password or login error. Please check and try again");
        }
    }

    public ResponseEntity<?> verifyEmail(String email, String emailCode) {
        Optional<User> optionalUser = repository.findUserByEmail(email);
        if (!optionalUser.isPresent()) return notFound().build();
        User user = optionalUser.get();
        if (user.isEnabled()) return status(HttpStatus.CONFLICT).body("Account is already activated");
        if (user.getEmailCode().equals(emailCode)) {
            user.setEmailCode(null);
            user.setEnabled(true);
            repository.save(user);
            return ok("Account has been successfully activated.");
        }
        mailService.sendEmail(
                user.getEmail(), "Link to activate your email",
                String.format("http://localhost:8080/api/auth/verify?email=%s&emailCode=%s",
                        user.getEmail(), user.getEmailCode()));
        return ok("Email verification code error. We have sent the link back to your email");
    }

    public ResponseEntity<?> getMe(HttpServletRequest request) {
        User user = repository.getReferenceById(UUID.fromString(jwtProvider.getClaimsObjectFromToken(getToken(request)).getId()));
        return ok(new UserResponse(user));
    }

    public String getToken(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        return header.replace("Bearer ", "");
    }
}
