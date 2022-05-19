package com.yxjr.yxjrsqlitedemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yxjr.yxjrsqlitedemo.entity.UploadPackage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (UploadPackage)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-09 11:04:17
 */
@Mapper
public interface UploadPackageDao extends BaseMapper<UploadPackage> {
    //获取设备版本号
    UploadPackage getVersion(@Param("devId")String  devId);

}

