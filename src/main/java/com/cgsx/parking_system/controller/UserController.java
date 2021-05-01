package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.User;
import com.cgsx.parking_system.service.UserService;
import com.cgsx.parking_system.util.DefinitionException;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    public Result getInfo(@RequestParam(name = "token") String username){
        log.info("【用户信息接口】:" + username);
        return new Result().success("成功", userService.getUserByUserName(username));
    }

    @RequestMapping("/listUser")
    public Result listUser(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int size
    ){
        log.info("【用户列表接口】:keyword:"+keyword+"page:"+page+"limit:"+size);
        return new Result().success("查询成功", userService.findAllUser(keyword, page, size));
    }

    @PostMapping("/update")
    public Result updateUser(@RequestBody User user){
        log.info("请求接口update,body:"+user.toString());
        userService.updateUser(user);
        return new Result().success("更新成功", user);
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user){
        log.info("请求接口addUser,body:"+user.toString());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String encodePassword = encoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        if(userService.getUserByUserName(user.getUsername()) != null){
            throw new DefinitionException(400, "用户名已存在");
        }
        userService.updateUser(user);
        return new Result().success("添加成功", user);
    }
}
