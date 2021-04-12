package com.cgsx.parking_system.controller;

import com.alibaba.fastjson.JSON;
import com.cgsx.parking_system.entity.*;
import com.cgsx.parking_system.service.*;
import com.cgsx.parking_system.util.DefinitionException;
import com.cgsx.parking_system.util.ErrorEnum;
import com.cgsx.parking_system.util.Result;
import com.cgsx.parking_system.util.StrUnicodeToUtf8;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    private RecordService recordService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private ChartDataService chartDataService;

    @RequestMapping("/listCar")
    public Result listCar(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "page", defaultValue = "0")Integer pageNumber,
            @RequestParam(name = "limit", defaultValue = "10")Integer pageSize) {

        log.info("请求接口listCar， 参数:"+"keyword:"+keyword+"page:"+pageNumber+"limit"+pageSize);
        return new Result().success("查询成功", carService.getCar(keyword, pageNumber, pageSize));
    }

    @RequestMapping("/listSortCar")
    public Result listSortCar(@RequestParam(name = "page", defaultValue = "0")Integer pageNumber,
                              @RequestParam(name = "limit", defaultValue = "10")Integer pageSize,
                              @RequestParam(name = "sorts", defaultValue = "carTimes")String sorts){
        log.info("【车辆排序接口】:pageNumber:"+pageNumber+",pageSize:"+pageSize+",sorts:"+sorts);
        return new Result().success("查询成功", carService.getCarBySort(pageNumber, pageSize, sorts));
    }

    @PostMapping("/license")
    public Result license(MultipartFile file) throws IOException {
        String realFileName = file.getOriginalFilename();
        String url_path = "images" + File.separator + realFileName;

        //图片保存路径
        String savePath = "D:/parkingResources/test.jpg";
        log.info("图片保存地址："+savePath);

        File saveFile = new File(savePath);
        file.transferTo(saveFile);
        String license = restTemplate.getForEntity("http://localhost:3000/getLicense", String.class).getBody();
        license = StrUnicodeToUtf8.unicodeToUtf8(license);
        license = license.substring(1, license.length()-1);
        log.info(license);
//        license = "黑GFQR4N";
        Pattern pattern = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$");
        Matcher matcher = pattern.matcher(license);
        // 有车 识别成功
        if(matcher.matches()){
            Car car = carService.getCarByCarNum(license);
            if(car == null) {
                car = new Car();
                car.setCarNum(license);
                car.setCarTimes(1);
            }
            else {
                car.setCarTimes(car.getCarTimes() + 1);
            }
            carService.updateCar(car);
            Record record = recordService.getRecordByCarAndLeaveDate(car, null);
            Page<Member> memberPage = memberService.getMember(license, -1, 0, 10);
            if(record == null){
                record = new Record();
                record.setCar(car);
                record.setEnterDate(new Date());
                Page<Space> spacePage = spaceService.getSpace("", 0, 0, 10000);
                if(spacePage.getTotalElements() == 0)
                    return Result.otherError(ErrorEnum.SPACE_FULL_ERROR);
                Space space = spacePage.getContent().get(0);
                record.setSpace(space);
                space.setSpaceStatus(1);
                space.setCar(car);

                if(memberPage.getTotalElements() > 0){
                    Member member = memberPage.getContent().get(0);
                    if(member.getEndDate().getTime() >= new Date().getTime()){
                        record.setPayment(member.getMemberType());
                    }
                } else {
                    record.setPayment(0);
                }
                spaceService.updateSpace(space);
                log.info("【入口接口】车牌号:"+license+", 停车位置："+ space.getSpaceArea().getArea()+space.getSpaceNum()+"号");
            }
            else {
                long start = record.getEnterDate().getTime();
                long end = new Date().getTime();
                int hours = (int) ((end - start)/(1000 * 60 * 60));
                Calendar calendar = Calendar.getInstance();
                if(memberPage.getTotalElements() == 0){
                    chartDataService.updateChartData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 0, (hours * 5)+2);
                    record.setMoney((float) (hours * 5)+2);
                }else {
                    Member member = memberPage.getContent().get(0);
                    if(member.getEndDate().getTime() >= new Date().getTime()){
//                        record.setPayment(member.getMemberType());
                        if(member.getMemberType() == 1){
                            chartDataService.updateChartData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1, (hours * 3)+2);
                            record.setMoney((float) (hours * 3)+2);
                        }else if(member.getMemberType() == 2){
                            chartDataService.updateChartData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 2, (hours * 2)+2);
                            record.setMoney((float)(hours * 2)+2);
                        }
                    } else {
//                        record.setPayment(0);
                        chartDataService.updateChartData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 0, (hours * 5)+2);
                        record.setMoney((float) (hours * 5)+2);
                    }
                }
                Space space = record.getSpace();
                space.setSpaceStatus(0);
                space.setCar(null);
                record.setSpace(space);
                spaceService.updateSpace(space);
                record.setLeaveDate(new Date());
                log.info("【出口接口】车牌号："+license+",会员类别："+record.getPayment()+"花费"+record.getMoney());
            }
            recordService.updateRecord(record);
            return new Result().success("识别成功", record);
        }
        log.info("车牌识别错误");
        return null;
    }
}
