package code.uz.service;

import code.uz.dto.ResponseDTO;
import code.uz.dto.TaskGradeRequest;
import code.uz.dto.TaskGradeResponse;

import java.util.List;

public interface TaskGradeService {
    ResponseDTO<TaskGradeResponse> markStudent(String taskId, TaskGradeRequest taskGradeRequest);
    void deleteMarkedStudent(String markId);
    ResponseDTO<TaskGradeResponse> updateMarkedStudent(String markId, TaskGradeRequest taskGradeRequest);
    ResponseDTO<List<TaskGradeResponse>> getAllMarkedStudent();
    ResponseDTO<TaskGradeResponse> getMarkedStudentById(String markId);
}
