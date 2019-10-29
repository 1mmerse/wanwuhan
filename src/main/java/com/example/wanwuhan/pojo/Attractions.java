package com.example.wanwuhan.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Entity
@TableName("attractions")
public class Attractions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attractionId;
    private String attractName;
    private String attractIntroduction;
    private String titleImageUrl;
    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinTable(name = "s_attract_image", joinColumns={@JoinColumn(name="attractionId")}
            ,inverseJoinColumns={@JoinColumn(name="imageId")})
    private List<Images> attractImages;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "attractionId")
    private List<Comments> attractComments;

    public Integer getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Integer attractionId) {
        this.attractionId = attractionId;
    }

    public String getAttractName() {
        return attractName;
    }

    public void setAttractName(String attractName) {
        this.attractName = attractName;
    }

    public String getAttractIntroduction() {
        return attractIntroduction;
    }

    public void setAttractIntroduction(String attractIntroduction) {
        this.attractIntroduction = attractIntroduction;
    }

    public List<Images> getAttractImages() {
        return attractImages;
    }

    public void setAttractImages(List<Images> attractImages) {
        this.attractImages = attractImages;
    }

    public List<Comments> getAttractComments() {
        return attractComments;
    }

    public void setAttractComments(List<Comments> attractComments) {
        this.attractComments = attractComments;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }
}
