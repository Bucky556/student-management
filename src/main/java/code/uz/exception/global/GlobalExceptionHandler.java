package code.uz.exception.global;

import code.uz.dto.ResponseDTO;
import code.uz.exception.CustomException;
import code.uz.exception.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDTO<String>> handle(CustomException e) {
        return new ResponseEntity<>(
                ResponseDTO.error(e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleUser(UsernameNotFoundException e) {
        return new ResponseEntity<>(
                ResponseDTO.error(e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDTO.error("Validation error", errors)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("LocalDate")) {
            return ResponseEntity.badRequest().body(ResponseDTO.error("Birth date must follow format: yyyy-MM-dd", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDTO.error("Invalid error", null)
        );
    }
}
