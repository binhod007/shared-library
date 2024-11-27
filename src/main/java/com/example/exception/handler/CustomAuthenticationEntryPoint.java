package com.example.exception.handler;

import com.example.exception.enums.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException)
            throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"code\": \"" + Message.TOKEN_MISSING_OR_INVALID.getCode() + "\", \"message\": \"" + Message.TOKEN_MISSING_OR_INVALID.getDesc() + "\"}"
        );
    }
}
