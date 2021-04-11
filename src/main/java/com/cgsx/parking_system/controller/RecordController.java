package com.cgsx.parking_system.controller;

import com.alibaba.fastjson.annotation.JSONField;
import com.cgsx.parking_system.service.RecordService;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequestMapping("/record")
@Slf4j
public class RecordController {

    @Autowired
    private RecordService recordService;



    @RequestMapping("/listRecord")
    public Result listRecord(
            @RequestParam(name = "keyword", defaultValue = "")String keyword,
            @RequestParam(name = "payment", defaultValue = "-1")Integer payment,
            @RequestParam(name = "page", defaultValue = "0")Integer page,
            @RequestParam(name = "limit", defaultValue = "10")Integer size){
        log.info("请求接口listRecord,参数:"+keyword+" "+payment+" "+page+" "+size);
        return new Result().success("查询成功", recordService.getRecode(keyword, payment, page, size));
    }

    @RequestMapping("/enterDateRecord")
    public Result enterDateRecord(
            @RequestParam(name = "start", defaultValue = "")String start,
            @RequestParam(name = "end", defaultValue = "")String end,
            @RequestParam(name = "page", defaultValue = "0")Integer page,
            @RequestParam(name = "limit", defaultValue = "10")Integer size) throws ParseException {
        SimpleDateFormat  dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start1 = new Date(), end1 = new Date();
        if (start.trim().equals("") && end.trim().equals("")){
            end1.setHours(end1.getHours()+24);
        }
        else {
            start1 = dateTimeFormat.parse(start);
            end1 = dateTimeFormat.parse(end);
        }

        log.info(dateTimeFormat.format(start1));
        log.info(dateTimeFormat.format(end1));
        log.info("【进入时间段查询记录接口】:" + "开始时间"+ start+",结束时间"+end);
        return new Result().success("查询成功", recordService.getRecordByEnterDateBetween(start1, end1,  page, size));
    }


    @RequestMapping("/leaveDateRecord")
    public Result leaveDateRecord(
            @RequestParam(name = "start", defaultValue = "")String start,
            @RequestParam(name = "end", defaultValue = "")String end,
            @RequestParam(name = "page", defaultValue = "0")Integer page,
            @RequestParam(name = "limit", defaultValue = "10")Integer size) throws ParseException {
        SimpleDateFormat  dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start1 = new Date(), end1 = new Date();
        if (start.trim().equals("") && end.trim().equals("")){
            end1.setHours(end1.getHours()+24);
        }
        else {
            start1 = dateTimeFormat.parse(start);
            end1 = dateTimeFormat.parse(end);
        }

        log.info(dateTimeFormat.format(start1));
        log.info(dateTimeFormat.format(end1));
        log.info("【离开时间段查询记录接口】:" + "开始时间"+ start+",结束时间"+end);
        return new Result().success("查询成功", recordService.getRecordByLeaveDateBetween(start1, end1,  page, size));
    }
}
