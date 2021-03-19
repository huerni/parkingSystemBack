package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.Space;
import com.cgsx.parking_system.service.SpaceService;
import com.cgsx.parking_system.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/space")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @PostMapping("/updateSpace")
    public Result updateSpace(@RequestBody Space space){
        spaceService.updateSpace(space);

        return new Result().success("更新成功", space);
    }

    @RequestMapping("/listSpace")
    public Result querySpace(
            @RequestParam(name = "keyword", defaultValue = "")String keyword,
            @RequestParam(name = "spaceRemark", defaultValue = "-1")Integer spaceRemark,
            @RequestParam(name = "spaceStatus", defaultValue = "-1")Integer spaceStatus,
            @RequestParam(name = "page", defaultValue = "0")Integer pageNumber,
            @RequestParam(name = "limit", defaultValue = "10")Integer pageSize){
        System.out.println(spaceRemark);
        System.out.println(spaceStatus);

        return new Result().success("查询成功", spaceService.getSpace(keyword, spaceRemark, spaceStatus, pageNumber, pageSize));
    }
}
