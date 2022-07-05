package com.yxjr.yxjrDevOps.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yxjr.yxjrDevOps.entity.LogPackage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (LogPackage)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-09 11:03:24
 */
@Mapper
public interface LogPackageDao extends BaseMapper<LogPackage> {
    List<LogPackage> queryByDevId(String devId);
    boolean deleteByTime();
}

