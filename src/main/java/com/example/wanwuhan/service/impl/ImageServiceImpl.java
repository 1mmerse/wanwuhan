package com.example.wanwuhan.service.impl;

import com.example.wanwuhan.dao.ImagesDao;
import com.example.wanwuhan.pojo.Images;
import com.example.wanwuhan.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImagesDao imagesDao;

    @Override
    public List<String> uploadPic(MultipartFile[] multipartFiles, String secFile,String uploadPath) {
        List<String> urlList = new ArrayList<String>();
        String realpath = uploadPath + secFile;
        CreatFileDir(realpath);
        String filename = null;
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                if (!multipartFile.getOriginalFilename().equals("")) {
                    filename = UUID.randomUUID().toString().replaceAll("-", "") + multipartFile.getOriginalFilename();
                    multipartFile.transferTo(new File(realpath, filename));
                    urlList.add("1mmerse.cn/images"+secFile + "/" + filename);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return urlList;
    }

    @Override
    public void addImage(Images image) {
        imagesDao.save(image);
    }

    protected void CreatFileDir(String filepath) {
        File dir = new File(filepath);
        if (!dir.exists())
            dir.mkdirs();
    }
}
