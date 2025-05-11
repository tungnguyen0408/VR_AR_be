package bt1.web_ban_giay.util;

import bt1.web_ban_giay.dto.response.GlobalResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class FomatBeforeResponse implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse=((ServletServerHttpResponse) response).getServletResponse();
        int statusCode=servletResponse.getStatus();
        GlobalResponse<Object> res=new GlobalResponse<>();
        res.setStatus(statusCode);
        if(statusCode>=400){
            return body;
        }
        else {
            res.setMessage("goi api thanh cong");
            res.setData(body);
        }
        return res;
    }
}
