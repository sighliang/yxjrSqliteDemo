package com.yxjr.yxjrDevOps.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxjr.yxjrDevOps.entity.LogPackage;

import java.util.List;

/**
 * (LogPackage)表服务接口
 *
 * @author makejava
 * @since 2022-05-09 11:03:24
 */
public interface LogPackageService extends IService<LogPackage> {
    boolean saveInfo(String logName,String devId);
    List<LogPackage> queryByDevId(String devId);
    boolean deleteLog();
}

