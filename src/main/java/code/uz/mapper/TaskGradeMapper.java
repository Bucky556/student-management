package code.uz.mapper;

import code.uz.dto.ResponseDTO;
import code.uz.dto.TaskGradeResponse;
import code.uz.entity.TaskEntity;
import code.uz.entity.TaskGradeEntity;

public class TaskGradeMapper {
    public static ResponseDTO<TaskGradeResponse> toDTO(TaskGradeEntity taskGradeEntity, TaskEntity taskEntity) {
        TaskGradeResponse taskGradeResponse = new TaskGradeResponse();
        taskGradeResponse.setId(taskGradeEntity.getId());
        taskGradeResponse.setMark(taskGradeEntity.getMark());
        taskGradeResponse.setCreateDate(taskGradeEntity.getCreateDate());
        taskGradeResponse.setTaskId(taskEntity.getId());
        taskGradeResponse.setStudentId(taskEntity.getProfile().getId());
        return ResponseDTO.ok(taskGradeResponse);
    }
}
