package code.uz.service.Impl;

import code.uz.dto.ResponseDTO;
import code.uz.dto.TaskRequestDTO;
import code.uz.dto.TaskResponseDTO;
import code.uz.entity.ProfileEntity;
import code.uz.entity.TaskEntity;
import code.uz.exception.CustomException;
import code.uz.mapper.TaskMapper;
import code.uz.repository.ProfileRepository;
import code.uz.repository.TaskRepository;
import code.uz.service.TaskService;
import code.uz.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProfileRepository profileRepository;

    public ResponseDTO<TaskResponseDTO> createTask(TaskRequestDTO taskRequestDTO) {
        TaskEntity entity = new TaskEntity();
        entity.setTitle(taskRequestDTO.getTitle());
        entity.setContent(taskRequestDTO.getContent());

        String profileId = SecurityUtil.getID();
        Optional<ProfileEntity> byId = profileRepository.findById(profileId);
        if (byId.isEmpty()) {
            throw new CustomException("Profile does not exist");
        }
        ProfileEntity profile = byId.get();
        entity.setProfile(profile);
        TaskEntity taskEntity = taskRepository.save(entity);

        return TaskMapper.toDTO(taskEntity);
    }

    public ResponseDTO<List<TaskResponseDTO>> getAllTasks() {
        List<TaskEntity> profileId = taskRepository.findAllByProfileId(SecurityUtil.getID());
        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
        if (profileId.isEmpty()) {
            throw new CustomException("No Tasks found");
        }
        for (TaskEntity taskEntity : profileId) {
            taskResponseDTOS.add(TaskMapper.toDTO(taskEntity).getData());
        }
        return ResponseDTO.ok(taskResponseDTOS);
    }

    public ResponseDTO<List<TaskResponseDTO>> getTasksByAdmin(String profileId) {
        Optional<ProfileEntity> byId = profileRepository.findById(profileId);
        if (byId.isEmpty()) {
            throw new CustomException("Profile does not exist");
        }

        List<TaskEntity> profileId1 = taskRepository.findAllByProfileId(profileId);
        List<TaskResponseDTO> taskResponseDTOS = new ArrayList<>();
        if (profileId1.isEmpty()) {
            throw new CustomException("No Tasks found");
        }
        for (TaskEntity taskEntity : profileId1) {
            taskResponseDTOS.add(TaskMapper.toDTO(taskEntity).getData());
        }
        return ResponseDTO.ok(taskResponseDTOS);
    }

    public ResponseDTO<TaskResponseDTO> getTaskById(String taskId) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
        if (taskEntity.isEmpty()) {
            throw new CustomException("Task does not exist");
        }

        String profileId = SecurityUtil.getID();
        boolean isAdmin = SecurityUtil.hasRole("ROLE_ADMIN");
        boolean isOwner = taskEntity.get().getProfile().getId().equals(profileId);
        if (!isAdmin && !isOwner) {
            throw new CustomException("You do not have permission to access this task");
        }
        return TaskMapper.toDTO(taskEntity.get());
    }

    public ResponseDTO<TaskResponseDTO> updateTask(String taskId, TaskRequestDTO taskRequestDTO) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
        if (taskEntity.isEmpty()) {
            throw new CustomException("Task does not exist");
        }
        String profileId = SecurityUtil.getID();
        if (!taskEntity.get().getProfile().getId().equals(profileId) && !SecurityUtil.hasRole("ROLE_ADMIN")) {
            throw new CustomException("You do not have permission to update this task");
        }

        if (taskRequestDTO.getTitle() != null) {
            taskEntity.get().setTitle(taskRequestDTO.getTitle());
        }
        if (taskRequestDTO.getContent() != null) {
            taskEntity.get().setContent(taskRequestDTO.getContent());
        }
        taskRepository.save(taskEntity.get());
        return TaskMapper.toDTO(taskEntity.get());
    }

    public void deleteTask(String taskId) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(taskId);
        if (taskEntity.isEmpty()) {
            throw new CustomException("Task does not exist");
        }
        String profileId = SecurityUtil.getID();
        if (!taskEntity.get().getProfile().getId().equals(profileId) && !SecurityUtil.hasRole("ROLE_ADMIN")) {
            throw new CustomException("You do not have permission to delete this task");
        }
        taskRepository.delete(taskEntity.get());
        TaskMapper.toDTO(taskEntity.get());
    }
}
