package code.uz.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskGradeRequest {
    @NotNull(message = "Mark is required")
    @Min(value = 0, message = "Mark must be at least 0")
    @Max(value = 100, message = "Mark must not exceed 100")
    private Integer mark;
}
