package uz.uzbekistan.hrmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.uzbekistan.hrmanagement.entity.payload.InputOutputDto;
import uz.uzbekistan.hrmanagement.service.InputOutputService;

import java.util.UUID;

@RestController
@RequestMapping("/api/input/output")
public class InputOutputController {
    private final InputOutputService service;

    @Autowired
    public InputOutputController(InputOutputService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> addHistory(@Valid @RequestBody InputOutputDto inputOutputDto) {
        return service.saveInputOutputHistory(inputOutputDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllHistory(@RequestParam(name = "page", defaultValue = "1") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getInputOutputHistory(page, size);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserAllHistory(@PathVariable("userId") UUID id) {
        return service.getInputOutputForUser(id);
    }

}
