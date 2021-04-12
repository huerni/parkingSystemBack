package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {
    public Page<Car> getCar(String keyword,  int page, int size);

    public void updateCar(Car car);

    Car getCarByCarNum(String carNum);

    Page<Car> getCarBySort(int page, int size, String sorts);
}
