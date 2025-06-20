package code.uz.controller;

import code.uz.dto.ResponseDTO;
import code.uz.dto.TaskRequestDTO;
import code.uz.dto.TaskResponseDTO;
import code.uz.group_interface.OnCreate;
import code.uz.group_interface.OnUpdate;
import code.uz.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> createTask(@RequestBody @Validated(OnCreate.class) TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.createTask(taskRequestDTO));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<List<TaskResponseDTO>>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/all-byAdmin/{profileId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<List<TaskResponseDTO>>> getAllTasksByAdmin(@PathVariable("profileId") String projectId) {
        return ResponseEntity.ok(taskService.getTasksByAdmin(projectId));
    }

    @GetMapping("/byId/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STUDENT')")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> getTaskById(@PathVariable("taskId") String taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @PatchMapping("/update/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<TaskResponseDTO>> updateTask(@PathVariable("taskId") String taskId, @RequestBody @Validated(OnUpdate.class) TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskRequestDTO));
    }

    @DeleteMapping("/delete/{taskId}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT','ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteTask(@PathVariable("taskId") String taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(true);
    }
}
