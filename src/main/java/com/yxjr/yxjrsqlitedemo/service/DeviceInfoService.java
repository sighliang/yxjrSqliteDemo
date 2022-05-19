package com.yxjr.yxjrsqlitedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxjr.yxjrsqlitedemo.entity.DeviceInfo;

/**
 * (DeviceInfo)表服务接口
 *
 * @author makejava
 * @since 2022-05-11 13:52:40
 */
public interface DeviceInfoService extends IService<DeviceInfo> {



    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DeviceInfo queryById(Integer id);


    /**
     * 新增数据
     *
     * @param deviceInfo 实例对象
     * @return 实例对象
     */
    int insert(DeviceInfo deviceInfo);

    /**
     * 修改数据
     *
     * @param deviceInfo 实例对象
     * @return 实例对象
     */
    boolean update(DeviceInfo deviceInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    DeviceInfo getByDevId(String devId);

    boolean updateVisitTime(DeviceInfo deviceInfo);

    String getVisitTime(String devId);

}
