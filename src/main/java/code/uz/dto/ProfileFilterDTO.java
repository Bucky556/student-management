package code.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthdayDateFrom;
    private LocalDate birthdayDateTo;
}
