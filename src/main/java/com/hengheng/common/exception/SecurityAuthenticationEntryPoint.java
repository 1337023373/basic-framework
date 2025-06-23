package com.hengheng.common.exception;


import com.hengheng.common.base.Result;
import com.hengheng.common.constant.ErrorCode;
import com.hengheng.common.utils.HttpUtils;
import com.hengheng.common.utils.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyw
 * @version 1.0
 */
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", HttpUtils.getOrigin());

        response.getWriter().print(JsonUtils.toJsonString(Result.error(ErrorCode.UNAUTHORIZED)));
    }
}
