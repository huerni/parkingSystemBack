package com.cgsx.parking_system.repository;

import com.cgsx.parking_system.entity.ChartData;
import com.cgsx.parking_system.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChartDataRepository extends JpaRepository<ChartData, Long>, JpaSpecificationExecutor<ChartData> {
    ChartData getChartDataByYearAndType(Integer year, Integer type);
}
