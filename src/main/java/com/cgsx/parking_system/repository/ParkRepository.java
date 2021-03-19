package com.cgsx.parking_system.repository;

import com.cgsx.parking_system.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ParkRepository extends JpaRepository<Park, Long>, JpaSpecificationExecutor<Park> {
    Park findByParkId(Long parkId);
}
