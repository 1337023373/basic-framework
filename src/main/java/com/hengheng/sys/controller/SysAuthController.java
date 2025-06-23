package com.hengheng.sys.controller;


import com.hengheng.common.base.Result;
import com.hengheng.common.utils.TokenUtils;
import com.hengheng.sys.pojo.vo.SysAccountLoginVO;
import com.hengheng.sys.pojo.vo.SysCaptchaVO;
import com.hengheng.sys.pojo.vo.SysTokenVO;
import com.hengheng.sys.service.SysAuthService;
import com.hengheng.sys.service.SysCaptchaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyw
 * @version 1.0
 */
@RestController
@RequestMapping("auth")
@Tag(name = "认证管理")
@AllArgsConstructor
public class SysAuthController {
    private final SysCaptchaService sysCaptchaService;
    private final SysAuthService sysAuthService;

    @GetMapping("captcha")
    @Operation(summary = "验证码")
    public Result<SysCaptchaVO> captcha() {
        SysCaptchaVO captchaVO = sysCaptchaService.generate();
        return Result.ok(captchaVO);
    }

    @PostMapping("login")
    @Operation(summary = "账号密码登录")
    public Result<SysTokenVO> login(@RequestBody SysAccountLoginVO login) {
        SysTokenVO token = sysAuthService.loginByAccount(login);
        return Result.ok(token);
    }

    @PostMapping("logout")
    @Operation(summary = "退出")
    public Result<String> logout(HttpServletRequest request) {
        sysAuthService.logout(TokenUtils.getAccessToken(request));
        return Result.ok();
    }
}
