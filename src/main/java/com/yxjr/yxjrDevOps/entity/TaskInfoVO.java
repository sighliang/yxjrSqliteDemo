package com.yxjr.yxjrDevOps.entity;

import java.io.Serializable;
import java.util.List;

public class TaskInfoVO  implements Serializable{
    private static final long serialVersionUID = -6540567545789586843L;

    private String devId;

    private String branchId;

    private String branchName;

    private String ip;

    private List<TaskInfo> data;

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

    public List<TaskInfo> getData() {
        return data;
    }

    public void setData(List<TaskInfo> data) {
        this.data = data;
    }
}
