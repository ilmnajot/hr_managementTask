package uz.uzbekistan.hrmanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.uzbekistan.hrmanagement.entity.payload.TaskDto;
import uz.uzbekistan.hrmanagement.service.TaskService;

import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    @Secured({"ROLE_DIRECTOR", "ROLE_MANAGER"})
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(service.getAllTasks(page, size));
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getOneTask(@PathVariable("taskId") UUID uuid) {
        return service.getOneTask(uuid);
    }

    @GetMapping("/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllStatus() {
        return service.getAllTasksStatus();
    }

    @PostMapping
    @Secured({"ROLE_DIRECTOR", "ROLE_MANAGER"})
    public ResponseEntity<?> saveTask(@RequestBody @Valid TaskDto dto, HttpServletRequest request) {
        return service.addTask(dto, request);
    }

    @PutMapping("/{taskId}")
    @Secured({"ROLE_DIRECTOR", "ROLE_MANAGER"})
    public ResponseEntity<?> updateTask(@PathVariable("taskId") UUID uuid, @RequestBody @Valid TaskDto dto, HttpServletRequest request) {
        return service.editTask(uuid, dto, request);
    }

    @GetMapping("activated/{taskId}/{encodedEmail}")
    public ResponseEntity<?> activatedTask(@PathVariable("taskId") UUID id, @PathVariable("encodedEmail") String email) {
        return service.activatedTask(id, email);
    }

    //    Bu urlga faqat vazifa olishi mumkin bo'lgan userlar kira oladi
    @GetMapping("/complete/{taskId}")
    @Secured({"ROLE_MANAGER", "ROLE_OWNER"})
    public ResponseEntity<?> userTaskActivated(@PathVariable(name = "taskId") UUID id, HttpServletRequest request) {
        return service.userTaskIsActivated(id, request);
    }

//    O'Z VAQTIDA BAJARILMAGAN VAZIFALAR TUGALLANMAGAN VAZIFALAR RO'YHATI
    @GetMapping("/completed")
    @Secured({"ROLE_DIRECTOR", "ROLE_MANAGER"})
    public ResponseEntity<?> userTaskTimeActivated() {
        return service.usersTaskIsCompletedTime();
    }
}
