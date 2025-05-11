package bt1.web_ban_giay.dto.response;

import lombok.Data;

@Data
public class MetaDTO {
    private int page;
    private int pageSize;
    private int pages;
    private long total;
}
