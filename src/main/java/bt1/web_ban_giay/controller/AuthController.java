package bt1.web_ban_giay.controller;

import bt1.web_ban_giay.dto.request.LoginUserDTO;
import bt1.web_ban_giay.dto.request.UserTokenDTO;
import bt1.web_ban_giay.dto.response.AccountDTO;
import bt1.web_ban_giay.dto.response.ResLoginDTO;
import bt1.web_ban_giay.entity.User;
import bt1.web_ban_giay.repository.UserRepository;
import bt1.web_ban_giay.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Value("${bt1.cookie-expiration}")
    private long expriration_cookie;

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@RequestBody LoginUserDTO loginUserDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDTO.getUsername(), loginUserDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDB = userRepository.findByUsername(loginUserDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserTokenDTO res = new UserTokenDTO();
        res.setId(userDB.getId());
        res.setUsername(userDB.getUsername());
        res.setEmail(userDB.getEmail());

        // tao token
        String access_token = authService.createToken(res);
        ResLoginDTO resLoginDTO = new ResLoginDTO();
        resLoginDTO.setId(userDB.getId());
        resLoginDTO.setUsername(userDB.getUsername());
        resLoginDTO.setEmail(userDB.getEmail());
        resLoginDTO.setAddress(userDB.getAddress());
        resLoginDTO.setAcces_token(access_token);
        resLoginDTO.setPhone(userDB.getPhone());

        // luu cookie
        ResponseCookie responseCookie = ResponseCookie.from("access_token", access_token)
                .httpOnly(false)
                .path("/")
                .secure(true)
                .maxAge(expriration_cookie)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(resLoginDTO);
    }

    @GetMapping("/auth/get/account")
    public ResponseEntity<AccountDTO> getAccount() {
        return ResponseEntity.ok(authService.getAccount());
    }
}
