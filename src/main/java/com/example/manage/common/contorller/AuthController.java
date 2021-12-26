package com.example.manage.common.contorller;

import com.example.manage.common.payload.request.LoginRequest;
import com.example.manage.common.payload.request.RefreshRequest;
import com.example.manage.common.payload.response.JwtResponse;
import com.example.manage.core.encryption.Crypt;
import com.example.manage.core.security.jwt.JwtUtils;
import com.example.manage.core.security.service.UserDetailsImpl;
import com.example.manage.core.security.service.UserDetailsServiceImpl;
import com.example.manage.customer.repository.CustomerRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /**
     * 로그인
     * @param loginRequest
     * @return
     */
    @Operation(summary = "로그인", description = "로그인 요청(성공시 JWT Token 발급)")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        //Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, Crypt.decrypt(loginRequest.getPasswd()), userDetails.getAuthorities());
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, loginRequest.getPasswd(), userDetails.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken  = jwtUtils.generateJwtAccessToken(authentication);
        String refreshToken = jwtUtils.generateJwtRefreshToken(authentication);

        UserDetailsImpl principal = (UserDetailsImpl)authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .uid(principal.getUid())
                .email(principal.getEmail())
                .build()
        );
    }


    /**
     * Refresh Token을 사용한 Access Token 갱신
     * @param refreshRequest
     * @return
     * @throws Exception
     */
    @Operation(summary = "Access Token 갱신", description = "Refresh Token을 사용하여 Access Token 갱신")
    @PostMapping("/vk")
    public ResponseEntity<?> verifyKey(@Valid @RequestBody RefreshRequest refreshRequest) throws Exception {
        // 전달받은 refresh token으로 Auth 조회
        String refreshToken = refreshRequest.getR();

        if(jwtUtils.validateJwtToken(refreshToken)) {
            Map<String, Object> claims = jwtUtils.getClaimsFromJwtToken(refreshToken);
            String claimJsonString = MapUtils.getString(claims, "data");
            JsonElement e = JsonParser.parseString(Crypt.decrypt(claimJsonString));
            JsonObject claimJson = e.getAsJsonObject();

            String email = claimJson.get("email").getAsString();

            // 저장된 refresh token으로 username으로 access token 생성
            if(jwtUtils.validateJwtToken(refreshToken)) {
                UserDetailsImpl userDetails = UserDetailsImpl.build(customerRepository.findByEmail(email));
                String accessToken = jwtUtils.generateJwtAccessToken(userDetails);

                // refresh token의 만료 시간이 갱신 필요 기간 이하라면 refresh token도 갱신 및 저장
                if(jwtUtils.isNeedToRefresh(refreshToken)) {
                    refreshToken = jwtUtils.generateJwtRefreshToken(userDetails);
                }

                List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(JwtResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .uid(userDetails.getUid())
                        .email(userDetails.getEmail())
                        .build()
                );
            }
        }
        else {
            log.error(">>> Refresh Token 유효성 체크 오류 in verifyKey");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
