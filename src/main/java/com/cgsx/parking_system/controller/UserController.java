package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.User;
import com.cgsx.parking_system.service.UserService;
import com.cgsx.parking_system.util.DefinitionException;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
//        System.out.println(user);
        Result result = new Result();
        User user1 = userService.getUserByUserName(user.getUsername());
        log.info("请求接口login,body:"+user.toString());
//        System.out.println(user.getPassword());
        if(user1.getPassword().equals(user.getPassword())) {
            return result.success("登陆成功", user);
        }
        else {
            throw new DefinitionException(400,"密码不正确");
        }
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
        if(userService.getUserByUserName(user.getUsername()) != null){
            throw new DefinitionException(400, "用户名已存在");
        }
        userService.updateUser(user);
        return new Result().success("添加成功", user);
    }
}
