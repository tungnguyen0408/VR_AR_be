package bt1.web_ban_giay.dto.response;

import lombok.Data;

@Data
public class ResLoginDTO {
    private Long Id;
    private String username;
    private String email;
    private  String address;
    private String acces_token;
    private String phone;
}
