package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.Space;
import com.cgsx.parking_system.service.SpaceService;
import com.cgsx.parking_system.util.DefinitionException;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/space")
@Slf4j
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @PostMapping("/updateSpace")
    public Result updateSpace(@RequestBody Space space){
        spaceService.updateSpace(space);
        log.info("请求接口updateSpace,body:"+space.toString());
        return new Result().success("更新成功", space);
    }

    @PostMapping("/addSpace")
    public Result addSpace(@RequestBody Space space){
        log.info("请求接口addSpace,body:"+space.toString());
        if(spaceService.getSpace(space.getSpaceNum(), -1, -1, 0, 10) != null) {
            throw new DefinitionException(400, "编号已存在");
        }

        spaceService.updateSpace(space);
        return new Result().success("添加成功", space);
    }

    @RequestMapping("/listSpace")
    public Result querySpace(
            @RequestParam(name = "keyword", defaultValue = "")String keyword,
            @RequestParam(name = "spaceRemark", defaultValue = "-1")Integer spaceRemark,
            @RequestParam(name = "spaceStatus", defaultValue = "-1")Integer spaceStatus,
            @RequestParam(name = "page", defaultValue = "0")Integer pageNumber,
            @RequestParam(name = "limit", defaultValue = "10")Integer pageSize){

        log.info("请求接口listSpace,参数:keyword:"+keyword+"spaceRemark"+spaceRemark+"spaceStatus"+spaceStatus+"pageNumber"+pageNumber+"pageSize"+pageSize);
        return new Result().success("查询成功", spaceService.getSpace(keyword, spaceRemark, spaceStatus, pageNumber, pageSize));
    }
}
