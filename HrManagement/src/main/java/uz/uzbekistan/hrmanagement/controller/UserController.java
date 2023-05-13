package uz.uzbekistan.hrmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzbekistan.hrmanagement.entity.payload.RegisterDto;
import uz.uzbekistan.hrmanagement.service.AuthService;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final AuthService service;

    @Autowired
    public UserController(AuthService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<?> getAllUser(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getUsers(page, size);
    }

    @GetMapping("/{userId}")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_DIRECTOR', 'ROLE_MANAGER')")
    public ResponseEntity<?> getUser(@PathVariable("userId") UUID id) {
        return service.getUser(id);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMe(HttpServletRequest request) {
        return service.getMe(request);
    }

    @PostMapping
    @Secured({"ROLE_DIRECTOR", "ROLE_MANAGER"})
    public ResponseEntity<?> addUser(@RequestBody @Valid RegisterDto registerDTO, HttpServletRequest request) {
        return service.saveUser(registerDTO, request);
    }
}
