package com.example.exception.handler;

import com.example.exception.enums.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
//import jakarta.*;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException)
            throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"code\": \"" + Message.ACCESS_DENIED.getCode() + "\", \"message\": \"" + Message.ACCESS_DENIED.getDesc() + "\"}"
        );
    }


}
