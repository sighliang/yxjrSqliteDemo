package com.yxjr.yxjrsqlitedemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yxjr.yxjrsqlitedemo.entity.DeviceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (DeviceInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-07 10:51:04
 */
@Mapper
public interface DeviceInfoDao extends BaseMapper<DeviceInfo> {

    DeviceInfo queryByDevId(String devId);

    boolean updateVisitTime(DeviceInfo deviceInfo);

    Date getVisitTime(String devId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    DeviceInfo queryById(Integer id);


    /**
     * 统计总行数
     *
     * @param deviceInfo 查询条件
     * @return 总行数
     */
    long count(DeviceInfo deviceInfo);

    /**
     * 新增数据
     *
     * @param deviceInfo 实例对象
     * @return 影响行数
     */
    int insert(DeviceInfo deviceInfo);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<DeviceInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<DeviceInfo> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<DeviceInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<DeviceInfo> entities);

    /**
     * 修改数据
     *
     * @param deviceInfo 实例对象
     * @return 影响行数
     */
    boolean update(DeviceInfo deviceInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    boolean deleteById(Integer id);

}

