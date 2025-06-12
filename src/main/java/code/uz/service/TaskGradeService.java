package code.uz.service;

import code.uz.dto.TaskGradeRequest;
import code.uz.dto.TaskGradeResponse;

import java.util.List;

public interface TaskGradeService {
    TaskGradeResponse markStudent(String taskId, TaskGradeRequest taskGradeRequest);
    void deleteMarkedStudent(String markId);
    TaskGradeResponse updateMarkedStudent(String markId, TaskGradeRequest taskGradeRequest);
    List<TaskGradeResponse> getAllMarkedStudent();
    TaskGradeResponse getMarkedStudentById(String markId);
}
