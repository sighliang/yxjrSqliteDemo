package com.yxjr.yxjrDevOps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (LogPackage)实体类
 *
 * @author makejava
 * @since 2022-05-10 09:02:39
 */
public class LogPackage implements Serializable {
    private static final long serialVersionUID = -45086225188289402L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    
    private String logName;
    
    private String url;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadTime;

    private String devId;

    private String retain1;
    
    private String retain2;
    
    private String retain3;
    
    private String retain4;
    
    private String retain5;
    
    private String retain6;
    


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
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





}

