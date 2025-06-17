package code.uz.service.Impl;

import code.uz.dto.ResponseDTO;
import code.uz.dto.TaskGradeRequest;
import code.uz.dto.TaskGradeResponse;
import code.uz.entity.ProfileEntity;
import code.uz.entity.TaskEntity;
import code.uz.entity.TaskGradeEntity;
import code.uz.exception.CustomException;
import code.uz.mapper.TaskGradeMapper;
import code.uz.repository.TaskGradeRepository;
import code.uz.repository.TaskRepository;
import code.uz.service.TaskGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskGradeServiceImpl implements TaskGradeService {
    private final TaskGradeRepository taskGradeRepository;
    private final TaskRepository taskRepository;

    public ResponseDTO<TaskGradeResponse> markStudent(String taskId, TaskGradeRequest taskGradeRequest) {
        Optional<TaskEntity> byId = taskRepository.findById(taskId);
        if (byId.isEmpty()) {
            throw new CustomException("Task not found");
        }
        TaskEntity taskEntity = byId.get();
        ProfileEntity profile = taskEntity.getProfile();
        if (profile == null) {
            throw new CustomException("Profile not found");
        }

        TaskGradeEntity taskGradeEntity = new TaskGradeEntity();
        taskGradeEntity.setTask(taskEntity);
        taskGradeEntity.setProfile(profile);
        taskGradeEntity.setMark(taskGradeRequest.getMark());
        taskGradeRepository.save(taskGradeEntity);

        return TaskGradeMapper.toDTO(taskGradeEntity, taskEntity);
    }

    public void deleteMarkedStudent(String markId) {
        Optional<TaskGradeEntity> byId = taskGradeRepository.findById(markId);
        if (byId.isEmpty()) {
            throw new CustomException("Task not found");
        }
        TaskGradeEntity taskGradeEntity = byId.get();
        ProfileEntity profile = taskGradeEntity.getProfile();
        if (profile == null) {
            throw new CustomException("Profile not found");
        }
        taskGradeRepository.delete(taskGradeEntity);
    }

    public ResponseDTO<TaskGradeResponse> updateMarkedStudent(String markId, TaskGradeRequest taskGradeRequest) {
        Optional<TaskGradeEntity> byId = taskGradeRepository.findById(markId);
        if (byId.isEmpty()) {
            throw new CustomException("Marked task not found");
        }
        TaskGradeEntity taskGradeEntity = byId.get();
        ProfileEntity profile = taskGradeEntity.getProfile();
        if (profile == null) {
            throw new CustomException("Profile not found");
        }
        taskGradeEntity.setMark(taskGradeRequest.getMark());
        taskGradeEntity.setProfile(profile);
        taskGradeRepository.save(taskGradeEntity);

        TaskGradeResponse taskGradeResponse = new TaskGradeResponse();
        taskGradeResponse.setId(taskGradeEntity.getId());
        taskGradeResponse.setTaskId(taskGradeEntity.getTask().getId());
        taskGradeResponse.setCreateDate(taskGradeEntity.getCreateDate());
        taskGradeResponse.setStudentId(taskGradeEntity.getProfile().getId());
        taskGradeResponse.setMark(taskGradeRequest.getMark());
        return ResponseDTO.ok(taskGradeResponse);
    }

    public ResponseDTO<List<TaskGradeResponse>> getAllMarkedStudent() {
        List<TaskGradeEntity> all = taskGradeRepository.findAll();
        List<TaskGradeResponse> taskGradeResponses = new ArrayList<>();
        if (all.isEmpty()) {
            throw new CustomException("Tasks not found");
        }
        for (TaskGradeEntity taskGradeEntity : all) {
            TaskGradeResponse taskGradeResponse = new TaskGradeResponse();
            taskGradeResponse.setId(taskGradeEntity.getId());
            taskGradeResponse.setTaskId(taskGradeEntity.getTask().getId());
            taskGradeResponse.setCreateDate(taskGradeEntity.getCreateDate());
            taskGradeResponse.setStudentId(taskGradeEntity.getProfile().getId());
            taskGradeResponse.setMark(taskGradeEntity.getMark());
            taskGradeResponses.add(taskGradeResponse);
        }
        return ResponseDTO.ok(taskGradeResponses);
    }

    public ResponseDTO<TaskGradeResponse> getMarkedStudentById(String markId) {
        Optional<TaskGradeEntity> byId = taskGradeRepository.findById(markId);
        if (byId.isEmpty()) {
            throw new CustomException("Task not found");
        }
        TaskGradeEntity taskGradeEntity = byId.get();
        ProfileEntity profile = taskGradeEntity.getProfile();
        if (profile == null) {
            throw new CustomException("Profile not found");
        }
        TaskGradeResponse taskGradeResponse = new TaskGradeResponse();
        taskGradeResponse.setId(taskGradeEntity.getId());
        taskGradeResponse.setTaskId(taskGradeEntity.getTask().getId());
        taskGradeResponse.setCreateDate(taskGradeEntity.getCreateDate());
        taskGradeResponse.setStudentId(taskGradeEntity.getProfile().getId());
        taskGradeResponse.setMark(taskGradeEntity.getMark());
        return ResponseDTO.ok(taskGradeResponse);
    }

}
