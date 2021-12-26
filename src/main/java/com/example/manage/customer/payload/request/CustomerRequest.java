package com.example.manage.customer.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class CustomerRequest {

    @Schema(description = "이메일")
    @NotEmpty(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;

    @Schema(description = "비밀번호")
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String passwd;

}
