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
public class TaskResponseDTO {
    private String id;
    private String title;
    private String content;
    private LocalDate createdDate;
    private String createdBy;
}
