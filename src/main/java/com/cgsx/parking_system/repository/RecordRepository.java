package com.cgsx.parking_system.repository;

import com.cgsx.parking_system.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;

public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {
    Page<Record> findAll(Pageable pageable);

    Page<Record> findAllByEnterDateBetween(Date start, Date end, Pageable pageable);
}
