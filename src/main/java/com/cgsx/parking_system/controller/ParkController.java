package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.Park;
import com.cgsx.parking_system.service.ParkService;
import com.cgsx.parking_system.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/park")
public class ParkController {

    @Autowired
    private ParkService parkService;

    @RequestMapping("/listPark")
    public Result listPark(){
        return new Result().success("查询成功", parkService.getPark(0L));
    }

    @PostMapping("/updatePark")
    public Result updatePark(@RequestBody Park park){
        return new Result().success("更新成功", park);
    }
}
