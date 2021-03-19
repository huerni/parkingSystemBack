package com.cgsx.parking_system.service;

import com.cgsx.parking_system.entity.User;

public interface UserService {
    public User getUserByUserName(String userName);

    public void updateUser(User user);
}
