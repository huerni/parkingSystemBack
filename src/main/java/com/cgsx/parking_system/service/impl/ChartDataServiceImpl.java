package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.ChartData;
import com.cgsx.parking_system.repository.ChartDataRepository;
import com.cgsx.parking_system.service.ChartDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChartDataServiceImpl implements ChartDataService {

    @Autowired
    private ChartDataRepository chartDataRepository;

    @Override
    public ChartData getByYearAndType(Integer year, Integer type) {
        return chartDataRepository.getChartDataByYearAndType(year, type);
    }

    @Override
    public void updateChartData(int year, int month, int type, int money) {
        log.info("【图表更新服务】:year"+year+",month"+month+",type"+type+",money"+money);
        ChartData chartData = chartDataRepository.getChartDataByYearAndType(year, type);
        if(chartData == null){
            chartData = new ChartData(null,type,year,0,0,0,0,0,0,0,0,0,0,0,0);
        }
        switch (month){
            case 1: chartData.setJan(chartData.getJan()+money); break;
            case 2: chartData.setFeb(chartData.getFeb()+money); break;
            case 3: chartData.setMar(chartData.getMar()+money); break;
            case 4: chartData.setApr(chartData.getApr()+money); break;
            case 5: chartData.setMay(chartData.getMay()+money); break;
            case 6: chartData.setJune(chartData.getJune()+money); break;
            case 7: chartData.setJuly(chartData.getJuly()+money); break;
            case 8: chartData.setAug(chartData.getAug()+money); break;
            case 9: chartData.setSept(chartData.getSept()+money); break;
            case 10: chartData.setOct(chartData.getOct()+money); break;
            case 11: chartData.setNovem(chartData.getNovem()+money); break;
            case 12: chartData.setDecem(chartData.getDecem()+money); break;
        }
        chartDataRepository.save(chartData);
    }
}
