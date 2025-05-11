package bt1.web_ban_giay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GlobalResponse<T> {
    private int status;
    private String message;
    private T data;
}