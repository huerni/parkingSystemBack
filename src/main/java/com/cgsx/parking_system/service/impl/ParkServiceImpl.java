package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.Park;
import com.cgsx.parking_system.repository.ParkRepository;
import com.cgsx.parking_system.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    private ParkRepository parkRepository;

    @Override
    public Park getPark(Long parkId) {
        return parkRepository.findByParkId(parkId);
    }

    @Override
    public void updatePark(Park park) {
        parkRepository.save(park);
    }
}
