package com.example.wanwuhan.service.impl;

import com.example.wanwuhan.dao.CommentsDao;
import com.example.wanwuhan.pojo.Comments;
import com.example.wanwuhan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentsDao commentsDao;
    @Override
    public void addComment(Comments comment) {
        commentsDao.save(comment);
    }

}
