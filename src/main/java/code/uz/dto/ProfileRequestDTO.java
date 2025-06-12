package code.uz.dto;

import code.uz.group_interface.OnCreate;
import code.uz.group_interface.OnUpdate;
import code.uz.model.GeneralRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileRequestDTO {
    @NotBlank(message = "Name required", groups = OnCreate.class)
    @Pattern(regexp = "^(?!\\s*$).+", message = "Name required", groups = OnUpdate.class)
    private String name;
    @NotBlank(message = "Surname required", groups = OnCreate.class)
    @Pattern(regexp = "^(?!\\s*$).+", message = "Surname required", groups = OnUpdate.class)
    private String surname;
    @NotBlank(message = "Phone required", groups = OnCreate.class)
    @Pattern(regexp = "^(?!\\s*$).+", message = "Phone required", groups = OnUpdate.class)
    private String phone;
    @NotBlank(message = "Password required", groups = OnCreate.class)
    @Pattern(regexp = "^(?!\\s*$).+", message = "Password required", groups = OnUpdate.class)
    private String password;
    @NotNull(message = "Birth Date required", groups = OnCreate.class)
    @Past(message = "Birth Date must be in the past", groups = {OnCreate.class, OnUpdate.class})
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdayDate;
    private GeneralRole role;
}
