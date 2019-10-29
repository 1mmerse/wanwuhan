package com.example.wanwuhan.service;

import com.example.wanwuhan.pojo.User;

import java.util.Optional;

public interface UserService {
    void addUser(User user);
    String findUserByOpenId(String openId);
    void updateUserInfo(User user);
}
