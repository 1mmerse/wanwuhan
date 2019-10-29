package com.example.wanwuhan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;

import javax.persistence.*;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * open_id
     */
    @Id
    private String openId;
    /**
     * skey
     */
    private String skey;
    /**
     * 创建时间
     */
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     * 最后登录时间
     */
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastVisitTime;
    /**
     * session_key
     */
    @Column
    private String sessionKey;
    /**
     * 市
     */
    @Column
    private String city;
    /**
     * 省
     */
    @Column
    private String province;
    /**
     * 国
     */
    @Column
    private String country;
    /**
     * 头像
     */
    @Column
    private String avatarUrl;
    /**
     * 性别
     */
    @Column
    private Integer gender;
    /**
     * 网名
     */
    @Column
    private String nickName;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "openId" )
    private List<Comments> commentsList;



    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }
}