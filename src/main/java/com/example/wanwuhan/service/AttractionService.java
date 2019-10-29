package com.example.wanwuhan.service;

import com.example.wanwuhan.pojo.Attractions;

import java.util.List;

public interface AttractionService {
    String findAllAttractions();
    void addAttraction(Attractions attraction);
    String findAttractionById(Integer attractionId);
}
