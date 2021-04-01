package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.service.SpaceAreaService;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/spaceArea")
public class SpaceAreaController {

    @Autowired
    private SpaceAreaService spaceAreaService;

    @RequestMapping("/listSpaceArea")
    public Result listSpaceArea(){

        return new Result().success("查询成功", spaceAreaService.getAllSpaceArea());
    }
}
