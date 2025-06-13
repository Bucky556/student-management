package code.uz.controller;

import code.uz.dto.ResponseDTO;
import code.uz.dto.TaskGradeRequest;
import code.uz.dto.TaskGradeResponse;
import code.uz.service.TaskGradeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-grade")
public class TaskGradeController {
    private final TaskGradeService taskGradeService;

    public TaskGradeController(TaskGradeService taskGradeService) {
        this.taskGradeService = taskGradeService;
    }

    @PostMapping("/mark/{taskId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<TaskGradeResponse>> markStudent(@PathVariable("taskId") String taskId, @RequestBody @Valid TaskGradeRequest taskGradeRequest) {
        return ResponseEntity.ok(taskGradeService.markStudent(taskId, taskGradeRequest));
    }

    @DeleteMapping("/delete/{taskId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable("taskId") String taskId) {
        taskGradeService.deleteMarkedStudent(taskId);
        return ResponseEntity.ok(true);
    }

    @PutMapping("update/{markedStudentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<TaskGradeResponse>> updateMarkedStudent(@PathVariable("markedStudentId") String markedStudentId, @RequestBody @Valid TaskGradeRequest taskGradeRequest) {
        return ResponseEntity.ok(taskGradeService.updateMarkedStudent(markedStudentId, taskGradeRequest));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<List<TaskGradeResponse>>> getAllMarkedStudents() {
        return ResponseEntity.ok(taskGradeService.getAllMarkedStudent());
    }

    @GetMapping("/byId/{markedStudentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<TaskGradeResponse>> getMarkedStudentById(@PathVariable("markedStudentId") String markedStudentId) {
        return ResponseEntity.ok(taskGradeService.getMarkedStudentById(markedStudentId));
    }
}
