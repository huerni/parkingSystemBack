package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpaceService {
//    Page<Space> findAllByPage(int page, int size);

    void updateSpace(Space space);

    Page<Space> getSpace(String keyword, Integer spaceRemark, Integer spaceStatus, int page, int size);
}
