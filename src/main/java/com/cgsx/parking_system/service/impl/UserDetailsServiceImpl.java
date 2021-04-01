package com.cgsx.parking_system.service.impl;


import com.cgsx.parking_system.entity.User;
import com.cgsx.parking_system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("【登录业务】:"+ username);
        User user = userService.getUserByUserName(username);
        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if(user == null)
            throw new RuntimeException("用户不存在");
        else {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
