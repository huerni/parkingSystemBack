package com.cgsx.parking_system.repository;

import com.cgsx.parking_system.entity.Space;
import com.cgsx.parking_system.entity.SpaceArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SpaceAreaRepository extends JpaRepository<SpaceArea, Long>, JpaSpecificationExecutor<SpaceArea> {
    List<SpaceArea> findAll();

    SpaceArea getSpaceAreaBySpaceAreaId(Long spaceAreaId);
}
