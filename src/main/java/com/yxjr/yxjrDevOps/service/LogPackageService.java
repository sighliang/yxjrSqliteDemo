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
    //查询设备id
    List<LogPackage> queryByDevId(String devId);
    //删除一个月之前的日志包
    boolean deleteLog();
    //删除指定设备的指定文件名的日志包
    boolean deleteLogFile(String fileName,String devId);
}

