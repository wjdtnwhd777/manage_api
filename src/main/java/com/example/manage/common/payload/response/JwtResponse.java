package com.example.manage.common.payload.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private String type = "Bearer";
    private String uid;
    private String email;

    @Builder
    public JwtResponse(String accessToken, String refreshToken, String uid, String email) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.uid = uid;
        this.email = email;
    }
}
