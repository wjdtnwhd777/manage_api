package com.example.manage.core.security.jwt;

import com.example.manage.core.encryption.Crypt;
import com.example.manage.core.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret")
    private String jwtSecret;

    @Value("${jwt.expiration.access}")
    private long expirationMsOfAccessToken;

    @Value("${jwt.expiration.refresh}")
    private long expirationMsOfRefreshToken;

    @Value("${jwt.refreshThreshold}")
    private int refreshThreshold;


    /**
     * Access Token 발급
     * @param authentication 인증 정보
     * @return
     */
    public String generateJwtAccessToken(Authentication authentication) throws Exception {
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();

        return generateJwtAccessToken(userPrincipal);
    }

    /**
     * Refresh Token 발급
     * @param authentication 인증 정보
     * @return
     */
    public String generateJwtRefreshToken(Authentication authentication) throws Exception {
        UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();

        return generateJwtRefreshToken(userPrincipal);
    }


    /**
     * Access Token 발급
     * @param principal 유저 정보
     * @return
     */
    public String generateJwtAccessToken(UserDetailsImpl principal) throws Exception {
        return setClaims(principal, expirationMsOfAccessToken);
    }

    /**
     * Refresh Token 발급
     * @param principal 인증 정보
     * @return
     */
    public String generateJwtRefreshToken(UserDetailsImpl principal) throws Exception {
        return setClaims(principal, expirationMsOfRefreshToken);
    }


    private String setClaims(UserDetailsImpl principal, long expirationMs) throws Exception {
        JSONObject claimJson = new JSONObject();
        claimJson.put("email", principal.getEmail());

        Map<String, Object> claims = new HashMap<>();
        claims.put("data", Crypt.encrypt(claimJson.toJSONString()));


        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }




    public boolean isNeedToRefresh(String token) {
        long remainingTimeMs = getRemainingTimeToExpiration(token);
        return remainingTimeMs <= refreshThreshold;
    }


    public Map<String, Object> getClaimsFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    public long getRemainingTimeToExpiration(String token) {
        try {
            if(validateJwtToken(token)) {
                long expirationMs = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration().getTime();
                long nowMs = (new Date()).getTime();

                return expirationMs - nowMs;
            }
        }
        catch(Exception e) {
            log.error(e.getLocalizedMessage());
        }

        return 0;
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error(">>> Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(">>> JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(">>> JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(">>> JWT claims string is empty: {}", e.getMessage());
        }

		return false;
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
