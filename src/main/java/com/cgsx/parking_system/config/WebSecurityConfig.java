package com.cgsx.parking_system.config;

import com.cgsx.parking_system.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true,jsr250Enabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomizeLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置认证方式等
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers("/member/memberLogin").permitAll()
                .antMatchers("/member/updateMember").permitAll()
                .antMatchers("/spaceArea/listSpaceArea").permitAll()
                .antMatchers("/space/listSpace").permitAll()
                .antMatchers("/car/license").permitAll()
                .antMatchers("/car/listSortCar").permitAll()
                .antMatchers("/chart/lineData").permitAll()
                .antMatchers("/user/addUser").hasAnyRole("SYSADMIN")
                .antMatchers("/space/addSpace").hasAnyRole("SYSADMIN")
                .antMatchers("/user/listUser").hasAnyRole("SYSADMIN")
                .and().formLogin().//登入
                permitAll().//允许所有用户
                successHandler(authenticationSuccessHandler).//登录成功处理逻辑
                failureHandler(authenticationFailureHandler).//登录失败处理逻辑
                and().logout().                 //登出
                permitAll().//允许所有用户
                logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
                deleteCookies("JSESSIONID").//登出之后删除cookie
//                antMatchers("/space").hasAuthority("ADMIN").
                //异常处理(权限拒绝、登录失效等)
                and().exceptionHandling().
                authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
        //会话管理
        and().sessionManagement().
                maximumSessions(1).//同一账号同时登录最大用户数
                expiredSessionStrategy(sessionInformationExpiredStrategy);//会话信息过期策略会话信息过期策略(账号被挤下线)
        //http相关的配置，包括登入登出、异常处理、会话管理等
        super.configure(http);
    }
}