package bt1.web_ban_giay.service;

import bt1.web_ban_giay.dto.request.UserTokenDTO;
import bt1.web_ban_giay.dto.response.AccountDTO;
import bt1.web_ban_giay.entity.User;
import bt1.web_ban_giay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtEncoder jwtEncoder;
    @Value("${bt1.jwt.expiration}")
    private long jwtExpiration;

    public String createToken(UserTokenDTO userDTO) {
        Instant now = Instant.now();
        Instant expriration = now.plus(jwtExpiration, ChronoUnit.SECONDS);
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(expriration)
                .claim("user", userDTO)
                .subject(userDTO.getUsername())
                .build();
        JwsHeader jwsHeader = JwsHeader.with(HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)).getTokenValue();
    }

    public AccountDTO getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "";
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new AccountDTO(user.getId(), user.getUsername(), user.getEmail(), user.getAddress());
    }
}
