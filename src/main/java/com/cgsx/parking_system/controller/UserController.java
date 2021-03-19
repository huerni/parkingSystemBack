package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.User;
import com.cgsx.parking_system.service.UserService;
import com.cgsx.parking_system.util.DefinitionException;
import com.cgsx.parking_system.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
//        System.out.println(user);
        Result result = new Result();
        User user1 = userService.getUserByUserName(user.getUsername());

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
        userService.updateUser(user);
        return new Result().success("更新成功", user);
    }
}
