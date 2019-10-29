package com.example.wanwuhan.service;

import com.example.wanwuhan.pojo.Images;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<String> uploadPic(MultipartFile[] multipartFiles, String secFile,String uploadPath);
    void addImage(Images image);
}
