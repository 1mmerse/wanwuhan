package com.example.wanwuhan.dao;

import com.example.wanwuhan.pojo.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsDao extends JpaRepository<Comments,Integer> {
}
