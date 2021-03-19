package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.Record;
import org.springframework.data.domain.Page;

public interface RecordService {
    Page<Record> getRecode(String keyword, int payment, int page, int size);
}
