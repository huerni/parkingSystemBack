package com.cgsx.parking_system.config;


import com.alibaba.fastjson.JSON;
import com.cgsx.parking_system.util.DefinitionException;
import com.cgsx.parking_system.util.ErrorEnum;
import com.cgsx.parking_system.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.otherError(ErrorEnum.NO_AUTH)));
    }
}
