package com.example.wanwuhan.controller;

import com.alibaba.fastjson.JSON;
import com.example.wanwuhan.pojo.Attractions;
import com.example.wanwuhan.pojo.Comments;
import com.example.wanwuhan.pojo.Images;
import com.example.wanwuhan.pojo.User;
import com.example.wanwuhan.service.AttractionService;
import com.example.wanwuhan.service.CommentService;
import com.example.wanwuhan.service.ImageService;
import com.example.wanwuhan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class commentUploadController {
    @Autowired
    private ImageService imageService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private AttractionService attractionService;
    private static final String brandFile = "/CommentImages";


    @RequestMapping("/commentTest")
    public String toUpload() {
        return "addComment";
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    @ResponseBody
    public String addComment(@RequestParam(value = "attractionId", required = true) Integer attractionId,
                                @RequestParam(value = "openid", required = true) String openid,
                                @RequestParam(value = "images", required = false) MultipartFile[] images,
                                @RequestParam(value = "commentContent", required = false) String commentContent,
                                HttpServletRequest request) {
        List<Images> commentImages = new ArrayList<>();
        Comments comment = new Comments();
        Attractions attraction = new Attractions();
        attraction = JSON.parseObject(attractionService.findAttractionById(attractionId), Attractions.class);
        User user = new User();
        user = JSON.parseObject(userService.findUserByOpenId(openid), User.class);
        comment.setAttraction(attraction);
        comment.setUser(user);
        comment.setCommentContent(commentContent);
        comment.setCommentTime(new Date());
        String uploadPath = request.getServletContext().getRealPath("/images"); //上传路径
        List<String> paths = imageService.uploadPic(images, brandFile, uploadPath);
        for (int i = 0; i < images.length; i++) {
            System.out.println(images[i].getOriginalFilename());
            if (!images[i].getOriginalFilename().equals("")) {
                Images imageComment = new Images();
                imageComment.setImageUrl(paths.get(i));
                imageService.addImage(imageComment);
                commentImages.add(imageComment);
            }
        }
        comment.setCommentImages(commentImages);
        //        try {
//            imageService.uploadPic(images, brandFile);
//        } catch (Exception e) {
//            //抛出自定义异常，交于全局异常处理器向前端返回Json数据
//            e.printStackTrace();
//        }
        commentService.addComment(comment);
        return "success";
    }
}
