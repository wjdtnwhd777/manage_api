package com.example.manage.common.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshRequest {

    @Schema(description = "발급받은 JWT Access Token")
    private String k;       // Access Token

    @Schema(description = "Access Token 갱신 여부 확인용 Refresh Token")
    @NotBlank
    private String r;       // Refresh Token
}
