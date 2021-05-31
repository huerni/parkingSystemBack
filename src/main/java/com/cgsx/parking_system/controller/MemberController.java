package com.cgsx.parking_system.controller;

import com.cgsx.parking_system.entity.Car;
import com.cgsx.parking_system.entity.Member;
import com.cgsx.parking_system.service.CarService;
import com.cgsx.parking_system.service.ChartDataService;
import com.cgsx.parking_system.service.MemberService;
import com.cgsx.parking_system.util.DefinitionException;
import com.cgsx.parking_system.util.ErrorEnum;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CarService carService;

    @Autowired
    private ChartDataService chartDataService;

    @RequestMapping("/listMember")
    public Result listMember(
            @RequestParam(name = "keyword", defaultValue = "")String keyword,
            @RequestParam(name = "memberType", defaultValue = "-1")Integer memberType,
            @RequestParam(name = "page", defaultValue = "0")Integer page,
            @RequestParam(name = "limit", defaultValue = "10")Integer size){
        log.info("请求接口listMember， 参数:"+"keyword:"+keyword+"memberType:"+memberType+"page:"+page+"limit"+size);
        return new Result().success("查询成功", memberService.getMember(keyword, memberType, page, size));
    }

    @PostMapping("/updateMember")
    public Result updateMember(@RequestBody Member member){
        Car car = carService.getCarByCarNum(member.getCar().getCarNum());
        Member member1 = memberService.getMemberByPhone(member.getPhone());
        log.info("请求接口updateMember,body:"+member.toString());
        log.info(car.toString());
        if(car != null && car.getCarOwner()!= null && !car.getCarOwner().equals(member.getCar().getCarOwner())) {
            return Result.otherError(ErrorEnum.CAR_OWNER_ERROR);
        }
        if(car == null) {
            carService.updateCar(member.getCar());
        }
        else {
//            if(member1 == null){
//                return Result.otherError(ErrorEnum.CAR_EXIST);
//            }
            if(car.getCarOwner() == null){
                car.setCarOwner(member.getCar().getCarOwner());
                carService.updateCar(car);
            }
            member.setCar(car);
        }
//        long daysDiff = ChronoUnit.DAYS.between(member.getOpenDate(), member.getEndDate());
        Calendar calendar = Calendar.getInstance();
        chartDataService.updateChartData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, member.getMemberType(), member.getMoney());
        memberService.updateMember(member);
        log.info("请求接口updateMember,body:"+member.toString());
        return new Result().success("充值成功", member);
    }

    @PostMapping("/addMember")
    public Result addMember(@RequestBody Member member){
//        memberService.updateMember(member);
        log.info("请求接口addMember,body:" + member.toString());
        return null;
//        return new Result().success("添加成功", member);
    }

    @RequestMapping("/memberLogin")
    public Result getMemberByPhone(@RequestParam(name = "phone", defaultValue = "")String phone){
        log.info("【电话登陆】："+ phone);
        return new Result().success("成功", memberService.getMemberByPhone(phone));
    }
}
