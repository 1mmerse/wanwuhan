package com.example.wanwuhan.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.wanwuhan.dao.AttractionDao;
import com.example.wanwuhan.pojo.Attractions;
import com.example.wanwuhan.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AttractionServiceImpl implements AttractionService {

    @Autowired
    private AttractionDao attractionDao;
    @Override
    public String findAllAttractions() {
        String jsonAttractions = JSON.toJSONString(attractionDao.findAll());
        return jsonAttractions;
    }

    @Override
    public void addAttraction(Attractions attraction) {
        attractionDao.save(attraction);
    }

    @Override
    public String findAttractionById(Integer attractionId) {
        String jsonAttraction = JSON.toJSONString(attractionDao.findById(attractionId));
        return jsonAttraction;
    }

}
