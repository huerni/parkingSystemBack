package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.ChartData;

public interface ChartDataService {
    ChartData getByYearAndType(Integer year, Integer type);

    void updateChartData(int year, int month, int type, int money);
}
