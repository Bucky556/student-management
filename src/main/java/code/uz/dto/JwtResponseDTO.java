package code.uz.dto;

import code.uz.model.GeneralRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponseDTO {
    private String name;
    private String surname;
    private String phone;
    private GeneralRole role;
    private String accessToken;
    private String refreshToken;
}
