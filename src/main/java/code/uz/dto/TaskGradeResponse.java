package code.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class TaskGradeResponse {
    private String id;
    private Integer mark;
    private LocalDate createDate;
    private String taskId;
    private String studentId;
}
