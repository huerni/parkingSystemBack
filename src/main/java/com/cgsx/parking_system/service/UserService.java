package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    public User getUserByUserName(String userName);

    public void updateUser(User user);

    Page<User> findAllUser(String keyword, int page, int size);
}
