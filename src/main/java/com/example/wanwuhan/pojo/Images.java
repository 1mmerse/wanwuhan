package com.example.wanwuhan.pojo;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@TableName("images")
public class Images implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;
    private String imageUrl;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
