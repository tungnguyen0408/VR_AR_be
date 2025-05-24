package bt1.web_ban_giay.exception;

import bt1.web_ban_giay.dto.response.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = InvalidException.class)
    public ResponseEntity<GlobalResponse<Object>> handleInvalitException(InvalidException invalidException){
        GlobalResponse<Object> res=new GlobalResponse<>();
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setMessage(invalidException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalResponse<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        GlobalResponse<Object> response = new GlobalResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GlobalResponse<Object>> handleNotFound(ResourceNotFoundException ex) {
        GlobalResponse<Object> response = new GlobalResponse<>(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<Object>> handleAll(Exception ex) {
        GlobalResponse<Object> response = new GlobalResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
