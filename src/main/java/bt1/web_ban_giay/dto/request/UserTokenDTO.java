package bt1.web_ban_giay.dto.request;

import lombok.Data;

@Data
public class UserTokenDTO {
    private Long Id;
    private String username;
    private String email;
}
