package com.yxjr.yxjrDevOps.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (DeviceInfo)实体类
 *
 * @author makejava
 * @since 2022-05-11 13:57:43
 */
public class DeviceInfo implements Serializable {
    private static final long serialVersionUID = -60440032460862042L;
    
    private String devId;
    
    private String branchId;
    
    private String branchName;
    
    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date visitTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    
    private String retain1;
    
    private String retain2;
    
    private String retain3;
    
    private String retain4;
    
    private String retain5;
    
    private String retain6;
    
    private String retain7;
    
    private String retain8;
    
    private String retain9;
    
    private String retain10;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getRetain7() {
        return retain7;
    }

    public void setRetain7(String retain7) {
        this.retain7 = retain7;
    }

    public String getRetain8() {
        return retain8;
    }

    public void setRetain8(String retain8) {
        this.retain8 = retain8;
    }

    public String getRetain9() {
        return retain9;
    }

    public void setRetain9(String retain9) {
        this.retain9 = retain9;
    }

    public String getRetain10() {
        return retain10;
    }

    public void setRetain10(String retain10) {
        this.retain10 = retain10;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

