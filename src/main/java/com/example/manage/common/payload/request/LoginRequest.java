package com.example.manage.common.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @Schema(description = "로그인할 아이디(이메일)")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;

    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String passwd;
}
