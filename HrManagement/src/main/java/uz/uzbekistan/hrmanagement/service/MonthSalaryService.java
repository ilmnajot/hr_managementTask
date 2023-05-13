package uz.uzbekistan.hrmanagement.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.uzbekistan.hrmanagement.entity.Month;
import uz.uzbekistan.hrmanagement.entity.MonthSalary;
import uz.uzbekistan.hrmanagement.entity.User;
import uz.uzbekistan.hrmanagement.entity.payload.MonthSalaryDto;
import uz.uzbekistan.hrmanagement.jwt.JWTProvider;
import uz.uzbekistan.hrmanagement.repository.MonthSalaryRepository;
import uz.uzbekistan.hrmanagement.repository.MothRepository;
import uz.uzbekistan.hrmanagement.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

@Service
public class MonthSalaryService {
    private final MonthSalaryRepository repository;
    private final UserRepository userRepository;
    private final MothRepository monthRepository;
    private final JWTProvider jwtProvider;

    @Autowired
    public MonthSalaryService(MonthSalaryRepository repository, UserRepository userRepository, MothRepository monthRepository, JWTProvider jwtProvider) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.monthRepository = monthRepository;
        this.jwtProvider = jwtProvider;
    }

    public ResponseEntity<?> addSalary(MonthSalaryDto dto) {
        Optional<User> optionalOwner = userRepository.findById(dto.getOwnerId());
        if (!optionalOwner.isPresent()) return status(HttpStatus.NOT_FOUND).body("Owner not found");
        Optional<Month> optionalMonth = monthRepository.findById(dto.getMonthId());
        if (!optionalMonth.isPresent()) return status(HttpStatus.NOT_FOUND).body("Mavjud bo'lmagan oy kiritildi");
        if (dto.getAmount() > 0)
            return badRequest().body("User uchun kiritilayotgan oylik miqdori 0dan oshiq holatda bo'lishi shart");
        return status(HttpStatus.CREATED).body(repository.save(new MonthSalary(dto.getAmount(), optionalMonth.get(), optionalOwner.get())));
    }

    public ResponseEntity<?> getMeSalary(HttpServletRequest request) {
        Claims claims = jwtProvider.getClaimsObjectFromToken(request.getHeader(HttpHeaders.AUTHORIZATION).substring(7));
        return ResponseEntity.ok(repository.findAllByOwner_Id(UUID.fromString(claims.getId())));
    }

    public ResponseEntity<?> getOneSalary(UUID id) {
        if (!userRepository.existsById(id)) return status(HttpStatus.NOT_FOUND).body("User not found");
        return ok(repository.findAllByOwner_Id(id));
    }
//    oylik belgilangandan so'ng uni tahrirlash o'chirish mumkin emas. Agar o'chirilsa yoki tahrirlash oqibati mablag'ni yeb yuborilishiga olib keladi
}
