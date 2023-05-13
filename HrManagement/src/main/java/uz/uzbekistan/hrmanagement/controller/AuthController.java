package uz.uzbekistan.hrmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzbekistan.hrmanagement.entity.payload.LoginDto;
import uz.uzbekistan.hrmanagement.service.AuthService;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginToSystem(@RequestBody @Valid LoginDto loginDto) {
        return service.login(loginDto);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam(name = "email") String email, @RequestParam(name = "emailCode") String emailCode) {
        return service.verifyEmail(email, emailCode);
    }
}
