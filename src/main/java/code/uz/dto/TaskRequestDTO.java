package code.uz.dto;

import code.uz.group_interface.OnCreate;
import code.uz.group_interface.OnUpdate;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequestDTO {
    @Pattern(regexp = "^(?!\\s*$).+", message = "Title required", groups = {OnUpdate.class,OnCreate.class})
    private String title;
    @Pattern(regexp = "^(?!\\s*$).+", message = "Content required", groups = {OnUpdate.class,OnCreate.class})
    private String content;
}
