package com.cgsx.parking_system.config;

import com.alibaba.fastjson.JSON;
import com.cgsx.parking_system.util.ErrorEnum;
import com.cgsx.parking_system.util.Result;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountExpiredException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        //返回json数据
        Result result = null;
        if (e instanceof BadCredentialsException) {
            //密码错误
            result = Result.otherError(ErrorEnum.USER_CREDENTIALS_ERROR);
        }
        else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            result = Result.otherError(ErrorEnum.USER_ACCOUNT_NOT_EXIST);
        }
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}