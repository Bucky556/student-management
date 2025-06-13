package code.uz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private T data;
    private String message;
    private String reason;
    private boolean success;
    private long totalRecords = 0;

    public ResponseDTO(String reason, boolean success) {
        this.reason = reason;
        this.success = success;
    }

    public ResponseDTO(T data, String reason, boolean success) {
        this.data = data;
        this.reason = reason;
        this.success = success;
    }

    public static <T> ResponseDTO<T> ok(T data) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(Boolean.TRUE);
        responseDTO.setData(data);
        return responseDTO;
    }

    public static <T> ResponseDTO<T> error(String reason, T data) {
        ResponseDTO<T> responseDTO = new ResponseDTO<>();
        responseDTO.setSuccess(Boolean.FALSE);
        responseDTO.setReason(reason);
        responseDTO.setData(data);
        return responseDTO;
    }
}
