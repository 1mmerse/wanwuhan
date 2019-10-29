package com.example.wanwuhan.dao;

import com.example.wanwuhan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,String> {
}
