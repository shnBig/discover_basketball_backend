package com.gain.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户登录请求 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户账号密码登录请求")
public class UserLoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "账号 (用户名或手机号)")
    private String account;

    @Schema(description = "账号")
    private String username;

    @Schema(description = "密码")
    private String password;

    //判断是手机登录还是账号密码登录 0 手机登录 1 账号密码登录
    @Schema(description = "登录方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer loginType;

    @Schema(description = "国际区号", example = "+86")
    private String countryCode = "+86";

    // 如果你有图形验证码，可以在这里扩展
    @Schema(description = "验证码（可选）")
    private String captcha;

}