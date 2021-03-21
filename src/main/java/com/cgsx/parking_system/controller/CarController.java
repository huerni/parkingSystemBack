package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.Car;
import com.cgsx.parking_system.service.CarService;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping("/listCar")
    public Result listCar(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0")Integer pageNumber,
            @RequestParam(name = "limit", defaultValue = "10")Integer pageSize) {

        log.info("请求接口listCar， 参数:"+"keyword:"+keyword+"page:"+pageNumber+"limit"+pageSize);
        return new Result().success("查询成功", carService.getCar(keyword, pageNumber, pageSize));
    }

//    @RequestMapping("/updateCar")
//    public Result updateCar(@RequestBody Car car){
//        carService.updateCar(car);
//        return new Result().success("识别成功", car);
//    }
}
