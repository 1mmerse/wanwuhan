package com.example.wanwuhan.controller;

import com.alibaba.fastjson.JSON;
import com.example.wanwuhan.pojo.Attractions;
import com.example.wanwuhan.pojo.Comments;
import com.example.wanwuhan.pojo.Images;
import com.example.wanwuhan.service.AttractionService;
import com.example.wanwuhan.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AttractionController {

    @Autowired
    private ImageService imageService;


    private static final String brandFile = "/AttractionImages";


    @RequestMapping("/AttractionTest")
    public String toUpload() {
        return "addAttraction";
    }

    @Autowired
    private AttractionService attractionService;

    @RequestMapping("/findAllAttractions")
    @ResponseBody
    public String findAllAttractions() {
        return attractionService.findAllAttractions();
    }

    @RequestMapping(value = "/findAttractionById", method = RequestMethod.POST)
    @ResponseBody
    public String findAttractionById(@RequestParam(value = "attractionId", required = true) Integer attractionId) {
        return attractionService.findAttractionById(attractionId);
    }

    @RequestMapping(value = "/addAttraction", method = RequestMethod.POST)
    public void addAttraction(@RequestParam(value = "attractionName", required = false) String attractionName,
                              @RequestParam(value = "titleImage", required = false) MultipartFile titleImage,
                              @RequestParam(value = "images", required = false) MultipartFile[] images,
                              @RequestParam(value = "attractionIntroduction", required = false) String attractionIntroduction,
                              HttpServletRequest request) {
        List<Images> attractionImages = new ArrayList<>();
        Attractions attraction = new Attractions();
        attraction.setAttractName(attractionName);
        String titleImageName = UUID.randomUUID().toString().replaceAll("-", "") + titleImage.getOriginalFilename();
        String uploadPath = request.getServletContext().getRealPath("/images"); //上传路径
        File dir = new File(uploadPath + brandFile);
        if (!dir.exists())
            dir.mkdirs();
        try {
            titleImage.transferTo(new File(uploadPath + brandFile, titleImageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        attraction.setTitleImageUrl("1mmerse.cn/images" + brandFile + "/" + titleImageName);
        attraction.setAttractIntroduction(attractionIntroduction);
        List<String> paths = imageService.uploadPic(images, brandFile, uploadPath);
        for (int i = 0; i < images.length; i++) {
            System.out.println(images[i].getOriginalFilename());
            if (!images[i].getOriginalFilename().equals("")) {
                Images imageAttraction = new Images();
                imageAttraction.setImageUrl(paths.get(i));
                imageService.addImage(imageAttraction);
                attractionImages.add(imageAttraction);
            }
        }
        attraction.setAttractImages(attractionImages);
//        try {
//            imageService.uploadPic(images, brandFile);
//        } catch (Exception e) {
//            //抛出自定义异常，交于全局异常处理器向前端返回Json数据
//            e.printStackTrace();
//        }
        attractionService.addAttraction(attraction);
    }

    @RequestMapping(value = "/findCommentByAttractionId", method = RequestMethod.POST)
    @ResponseBody
    public String findCommentByAttractionId(@RequestParam(value = "attractionId", required = true) Integer attractId) {
        Attractions attraction = JSON.parseObject(attractionService.findAttractionById(attractId), Attractions.class);
        List<Comments> commentsListAttraction = new ArrayList<>();
        for (int i = 0; i < attraction.getAttractComments().size(); i++) {
            Comments comment = new Comments(attraction.getAttractComments().get(i).getCommentImages(),
                    attraction.getAttractComments().get(i).getCommentContent(),
                    attraction.getAttractComments().get(i).getCommentTime());
            commentsListAttraction.add(comment);
        }
        String jsonCommentToAttraction = JSON.toJSONStringWithDateFormat(commentsListAttraction, "yyyy-MM-dd HH:mm:ss");
        return jsonCommentToAttraction;
    }
}
