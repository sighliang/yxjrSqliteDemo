package com.yxjr.yxjrDevOps.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yxjr.yxjrDevOps.entity.UploadPackage;
import net.sf.json.JSONObject;

/**
 * (UploadPackage)表服务接口
 *
 * @author makejava
 * @since 2022-05-09 11:04:17
 */
public interface UploadPackageService extends IService<UploadPackage> {
    boolean insertBy(int isAll,String[] devIds,String fileName,String version);
    JSONObject queryVersion(String devId);
    //锁定设备版本
    R lock( int id,String devId);
    //解锁设备版本
    R unlock( int id,String devId);

    //删除更新包文件
    boolean deleteFile(String fileName);
}

