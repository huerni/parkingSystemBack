package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.SpaceArea;
import com.cgsx.parking_system.repository.SpaceAreaRepository;
import com.cgsx.parking_system.service.SpaceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceAreaServiceImpl implements SpaceAreaService {

    @Autowired
    private SpaceAreaRepository spaceAreaRepository;

    @Override
    public List<SpaceArea> getAllSpaceArea() {

        return spaceAreaRepository.findAll();
    }

    @Override
    public SpaceArea getSpaceAreaById(Long spaceAreaId) {
        return spaceAreaRepository.getSpaceAreaBySpaceAreaId(spaceAreaId);
    }

}
