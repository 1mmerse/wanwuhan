package com.example.wanwuhan.dao;

import com.example.wanwuhan.pojo.Attractions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionDao extends JpaRepository<Attractions,Integer> {

}
