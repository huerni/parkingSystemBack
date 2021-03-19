package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.Park;

public interface ParkService {
    Park getPark(Long parkId);

    void updatePark(Park park);
}
