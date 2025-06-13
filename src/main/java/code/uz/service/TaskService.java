package code.uz.service;

import code.uz.dto.ResponseDTO;
import code.uz.dto.TaskRequestDTO;
import code.uz.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {
    ResponseDTO<TaskResponseDTO> createTask(TaskRequestDTO taskRequestDTO);
    ResponseDTO<List<TaskResponseDTO>> getAllTasks();
    ResponseDTO<List<TaskResponseDTO>> getTasksByAdmin(String profileId);
    ResponseDTO<TaskResponseDTO> getTaskById(String taskId);
    ResponseDTO<TaskResponseDTO> updateTask(String taskId, TaskRequestDTO taskRequestDTO);
    void deleteTask(String taskId);
}
