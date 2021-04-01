package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.SpaceArea;
import com.cgsx.parking_system.repository.SpaceAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface SpaceAreaService {

    public List<SpaceArea> getAllSpaceArea();

    public SpaceArea getSpaceAreaById(Long spaceAreaId);
}
