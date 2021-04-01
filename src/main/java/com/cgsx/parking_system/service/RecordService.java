package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.Record;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Date;

public interface RecordService {
    Page<Record> getRecode(String keyword, int payment, int page, int size);

    Page<Record> getRecordByEnterDateBetween(Date start, Date end, int page, int size);
}
