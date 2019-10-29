package com.example.wanwuhan.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@TableName("comments")
public class Comments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "openId")
    private User user;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "attractionId")
    private Attractions attraction;

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinTable(name = "s_comments_images", joinColumns={@JoinColumn(name="commentId")}
            ,inverseJoinColumns={@JoinColumn(name="imageId")})
    private List<Images> commentImages;

    private String commentContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date commentTime;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Attractions getAttraction() {
        return attraction;
    }

    public void setAttraction(Attractions attraction) {
        this.attraction = attraction;
    }

    public List<Images> getCommentImages() {
        return commentImages;
    }

    public void setCommentImages(List<Images> commentImages) {
        this.commentImages = commentImages;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Comments(List<Images> commentImages, String commentContent, Date commentTime) {
        this.commentImages = commentImages;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
    }
    public Comments(){

    }
}
