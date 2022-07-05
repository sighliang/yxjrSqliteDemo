package com.yxjr.yxjrDevOps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2022-05-09 11:04:29
 */
@SuppressWarnings("serial")
public class User extends Model<User> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String password;

    private Date visitTime;

    private Date createTime;

    private String role;

    private String retain1;

    private String retain2;

    private String retain3;

    private String retain4;

    private String retain5;

    private String retain6;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRetain1() {
        return retain1;
    }

    public void setRetain1(String retain1) {
        this.retain1 = retain1;
    }

    public String getRetain2() {
        return retain2;
    }

    public void setRetain2(String retain2) {
        this.retain2 = retain2;
    }

    public String getRetain3() {
        return retain3;
    }

    public void setRetain3(String retain3) {
        this.retain3 = retain3;
    }

    public String getRetain4() {
        return retain4;
    }

    public void setRetain4(String retain4) {
        this.retain4 = retain4;
    }

    public String getRetain5() {
        return retain5;
    }

    public void setRetain5(String retain5) {
        this.retain5 = retain5;
    }

    public String getRetain6() {
        return retain6;
    }

    public void setRetain6(String retain6) {
        this.retain6 = retain6;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

