package code.uz.dto;

import code.uz.model.GeneralRole;
import code.uz.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ProfileResponseDTO {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthdayDate;
    private Status status;
    private GeneralRole role;
    private LocalDate createdDate;
}
