package com.yxjr.yxjrsqlitedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxjr.yxjrsqlitedemo.entity.UploadPackage;
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
}

