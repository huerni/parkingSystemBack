package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.Car;
import com.cgsx.parking_system.entity.Space;
import com.cgsx.parking_system.repository.CarRepository;
import com.cgsx.parking_system.service.CarService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Page<Car> getCar(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "carId");
        return carRepository.findAll(this.getWhereClause(keyword), pageable);
    }

    @Override
    public void updateCar(Car car) {
        carRepository.save(car);
    }


    public Specification<Car> getWhereClause(String keyword) {
        return new Specification<Car>() {
            @Override
            public Predicate toPredicate(Root<Car> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
//                Join<Car, Space> sroot = root.join("space", JoinType.LEFT);
                /**
                 * 多表查询
                 */
                if (StringUtils.isNotBlank(keyword)) {
                    predicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.or(
                                            criteriaBuilder.like(root.get("carNum"), "%" + keyword + "%"),
                                            criteriaBuilder.like(root.get("carOwner"), "%" + keyword + "%")
//                                            criteriaBuilder.like(sroot.get("spaceArea"),"%" + keyword + "%"),
//                                            criteriaBuilder.like(sroot.get("spaceNum"),"%" + keyword + "%")
                                    )
                            )
                    );
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}
