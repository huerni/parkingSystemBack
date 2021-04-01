package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.Car;
import com.cgsx.parking_system.service.CarService;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/car")
@Slf4j
public class CarController {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }


    @Autowired
    private CarService carService;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/listCar")
    public Result listCar(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0")Integer pageNumber,
            @RequestParam(name = "limit", defaultValue = "10")Integer pageSize) {

        log.info("请求接口listCar， 参数:"+"keyword:"+keyword+"page:"+pageNumber+"limit"+pageSize);
        return new Result().success("查询成功", carService.getCar(keyword, pageNumber, pageSize));
    }

    @PostMapping("/license")
    public Result license(MultipartFile file) throws IOException {
        String realFileName = file.getOriginalFilename();
        String url_path = "images" + File.separator + realFileName;

        //图片保存路径
        String savePath = "D:/parkingResources/" + realFileName;
        log.info("图片保存地址："+savePath);

        File saveFile = new File(savePath);
        file.transferTo(saveFile);
        String license = restTemplate.getForEntity("http://localhost:3000/getLicense", String.class).getBody();
        log.info(license);
        return new Result().success("识别成功", license);
    }
}
