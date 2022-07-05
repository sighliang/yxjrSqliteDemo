package com.yxjr.yxjrDevOps.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yxjr.yxjrDevOps.entity.SelectVo;
import com.yxjr.yxjrDevOps.entity.TaskInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * (TaskInfo)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-07 14:12:52
 */
@Mapper
public interface TaskInfoDao extends BaseMapper<TaskInfo> {
    int count(SelectVo selectVo);
}

