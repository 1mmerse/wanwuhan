package com.example.wanwuhan.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.wanwuhan.dao.UserDao;
import com.example.wanwuhan.pojo.User;
import com.example.wanwuhan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    public String findUserByOpenId(String openId) {

        String jsonUser = JSON.toJSONString(userDao.findById(openId));
        return jsonUser;
    }

    @Override
    public void updateUserInfo(User user){
        userDao.save(user);
    }
}
