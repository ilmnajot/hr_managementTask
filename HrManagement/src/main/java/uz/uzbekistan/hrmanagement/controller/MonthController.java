package uz.uzbekistan.hrmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.uzbekistan.hrmanagement.repository.MothRepository;

@RestController
@RequestMapping("/api/month")
public class MonthController {
    private final MothRepository repository;

    @Autowired
    public MonthController(MothRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getMonths() {
        return ResponseEntity.ok(repository.findAll());
    }
}
