package com.cgsx.parking_system.service.impl;

import com.cgsx.parking_system.entity.User;
import com.cgsx.parking_system.repository.UserRepository;
import com.cgsx.parking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public void updateUser(User user){
        userRepository.save(user);
    }
}
