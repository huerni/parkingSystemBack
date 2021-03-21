package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.*;
import com.cgsx.parking_system.entity.Record;
import com.cgsx.parking_system.repository.RecordRepository;
import com.cgsx.parking_system.service.RecordService;
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
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;

    @Override
    public Page<Record> getRecode(String keyword, int payment, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "recordId");
        return recordRepository.findAll(this.getWhereClause(keyword, payment), pageable);
    }

    public Specification<Record> getWhereClause(String keyword, int payment){
        return new Specification<Record>() {
            @Override
            public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                /**
                 * 多表查询
                 */
                Join<Record, Car> carJoin = root.join("car", JoinType.LEFT);
                Join<Record, Space> spaceJoin = root.join("space", JoinType.LEFT);
                if(StringUtils.isNotBlank(keyword)){
                    predicates.add(
                            criteriaBuilder.and(
                                    criteriaBuilder.or(
                                            criteriaBuilder.like(carJoin.get("carOwner"),"%" + keyword + "%"),
                                            criteriaBuilder.like(carJoin.get("carNum"),"%" + keyword + "%"),
                                            criteriaBuilder.like(spaceJoin.get("spaceNum"), "%" + keyword + "%"),
                                            criteriaBuilder.like(spaceJoin.get("spaceArea"), "%" + keyword + "%")
                                    )
                            )
                    );
                }
                if(payment != -1)
                    predicates.add(criteriaBuilder.equal(root.get("payment"), payment));
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}
