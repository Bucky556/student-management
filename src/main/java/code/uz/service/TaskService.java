package code.uz.service;

import code.uz.dto.TaskRequestDTO;
import code.uz.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);
    List<TaskResponseDTO> getAllTasks();
    List<TaskResponseDTO> getTasksByAdmin(String profileId);
    TaskResponseDTO getTaskById(String taskId);
    TaskResponseDTO updateTask(String taskId, TaskRequestDTO taskRequestDTO);
    void deleteTask(String taskId);
}
