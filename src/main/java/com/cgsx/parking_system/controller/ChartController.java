package com.cgsx.parking_system.controller;

import com.alibaba.fastjson.JSON;
import com.cgsx.parking_system.entity.CakeChartData;
import com.cgsx.parking_system.entity.ChartData;
import com.cgsx.parking_system.entity.Record;
import com.cgsx.parking_system.service.ChartDataService;
import com.cgsx.parking_system.service.RecordService;
import com.cgsx.parking_system.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/chart")
@Slf4j
public class ChartController {
    @Autowired
    private RecordService recordService;
    @Autowired
    private ChartDataService chartDataService;


    @RequestMapping("/cakeData")
    public Result cakeData( @RequestParam(name = "start", defaultValue = "")String start,
                            @RequestParam(name = "end", defaultValue = "")String end) throws ParseException {
        SimpleDateFormat  dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start1 , end1;
        start1 = dateTimeFormat.parse(start);
        end1 = dateTimeFormat.parse(end);
        log.info("【饼图数据接口】"+"开始时间:"+start+",结束时间:"+end);
        Page<Record> recordPage = recordService.getRecordByEnterDateBetween(start1, end1,  0, 10000);
        int typePa = 0, typeMon = 0, typeYear = 0;
        if(recordPage.getTotalElements() > 0) {
            for (Record record : recordPage.getContent()) {
                if (record.getPayment() == 0)
                    typePa++;
                else if (record.getPayment() == 1)
                    typeMon++;
                else
                    typeYear++;
            }
        }
        List<CakeChartData> list = new ArrayList<>();
        list.add(new CakeChartData(typePa, "临时车"));
        list.add(new CakeChartData(typeMon, "月租车"));
        list.add(new CakeChartData(typeYear, "年租车"));
        return new Result().success("成功", list);
    }

    @RequestMapping("/lineData")
    public Result lineData(@RequestParam(name = "year", defaultValue = "2021") Integer year){
        ChartData chartData_0 = chartDataService.getByYearAndType(year, 0);
        ChartData chartData_1 = chartDataService.getByYearAndType(year, 1);
        ChartData chartData_2 = chartDataService.getByYearAndType(year, 2);
        List<Integer> typePaData, typeMonData, typeYearData;
        if(chartData_0 == null){
            typePaData = asList(0,0,0,0,0,0,0,0,0,0,0,0);
        }
        else{
            typePaData = asList(chartData_0.getJan(),
                    chartData_0.getFeb(),
                    chartData_0.getMar(),
                    chartData_0.getApr(),
                    chartData_0.getMay(),
                    chartData_0.getJune(),
                    chartData_0.getJuly(),
                    chartData_0.getAug(),
                    chartData_0.getSept(),
                    chartData_0.getOct(),
                    chartData_0.getNovem(),
                    chartData_0.getDecem());
        }

        if(chartData_1 == null){
            typeMonData = asList(0,0,0,0,0,0,0,0,0,0,0,0);
        }
        else {
            typeMonData = asList(chartData_1.getJan(),
                    chartData_1.getFeb(),
                    chartData_1.getMar(),
                    chartData_1.getApr(),
                    chartData_1.getMay(),
                    chartData_1.getJune(),
                    chartData_1.getJuly(),
                    chartData_1.getAug(),
                    chartData_1.getSept(),
                    chartData_1.getOct(),
                    chartData_1.getNovem(),
                    chartData_1.getDecem());
        }

        if(chartData_2 == null){
            typeYearData = asList(0,0,0,0,0,0,0,0,0,0,0,0);
        }
        else {
            typeYearData = asList(chartData_2.getJan(),
                    chartData_2.getFeb(),
                    chartData_2.getMar(),
                    chartData_2.getApr(),
                    chartData_2.getMay(),
                    chartData_2.getJune(),
                    chartData_2.getJuly(),
                    chartData_2.getAug(),
                    chartData_2.getSept(),
                    chartData_2.getOct(),
                    chartData_2.getNovem(),
                    chartData_2.getDecem());
        }
        
        Map<String, List> map = new HashedMap();
        map.put("typePaData", typePaData);
        map.put("typeMonData", typeMonData);
        map.put("typeYearData", typeYearData);
        log.info("【折线图数据接口】" + year);
        return new Result().success("成功", map);
    }
}
