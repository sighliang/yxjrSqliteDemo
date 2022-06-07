package com.yxjr.yxjrsqlitedemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.yxjrsqlitedemo.common.UploadConfig;
import com.yxjr.yxjrsqlitedemo.dao.UploadPackageDao;
import com.yxjr.yxjrsqlitedemo.entity.UploadPackage;
import com.yxjr.yxjrsqlitedemo.service.UploadPackageService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * (UploadPackage)表服务实现类
 *
 * @author makejava
 * @since 2022-05-09 11:04:17
 */
@Service("uploadPackageService")
public class UploadPackageServiceImpl extends ServiceImpl<UploadPackageDao, UploadPackage> implements UploadPackageService {

    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UploadConfig uploadConfig;
    @Autowired
    UploadPackageDao uploadPackageDao;

    @Override
    public boolean insertBy(int isAll, String[] devIds, String fileName,String version) {
        UploadPackage uploadPackage =new UploadPackage();
        try {
            if(isAll == 1){
                logger.info("更新包为全量更新包，开始保存全量更新包数据");
                uploadPackage.setPackageName(fileName);
                uploadPackage.setStatus(0);
                uploadPackage.setUploadTime(new Date());
                uploadPackage.setVersion(version);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String batchNO = formatter.format(new Date());
                uploadPackage.setBatchno(batchNO);
                String url="http://"+uploadConfig.getIp()+":"+uploadConfig.getPort()+"/common/download/"+fileName;
                uploadPackage.setUrl(url);
                return this.save(uploadPackage);
            }else if(isAll == 0){
                for(String devid:devIds){
                    uploadPackage.setPackageName(fileName);
                    uploadPackage.setStatus(1);
                    uploadPackage.setUploadTime(new Date());
                    uploadPackage.setDevId(devid);
                    uploadPackage.setVersion(version);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    String batchNO = formatter.format(new Date());
                    uploadPackage.setBatchno(batchNO);
                    String url="http://"+uploadConfig.getIp()+":"+uploadConfig.getPort()+"/common/download/"+fileName;
                    uploadPackage.setUrl(url);
                    boolean flag = this.save(uploadPackage);
                    logger.info("新建设备["+devid+"]的单量更新包结果：["+flag+"]");
                    if(!flag){
                        return false;
                    }
                }
                logger.info("保存设备的单量更新包成功");
                return true;
            }
        }catch (Exception e){
            logger.error("保存设备更新包失败,原因为："+e);
            return false;
        }
        return false;
    }

    @Override
    public JSONObject queryVersion(String devId) {
        logger.info("机器["+devId+"]获取版本号");
        JSONObject res = new JSONObject();
        try {
            UploadPackage uploadPackage=uploadPackageDao.getVersion(devId);
            if(uploadPackage==null){
                logger.info("数据库中无该设备的版本信息");
                res.put("retCode",-1);
                res.put("msg","数据库中无该设备的版本信息");
            }else{
                res.put("AppVersion",uploadPackage.getVersion());
                res.put("AppDownLoadFile",uploadPackage.getUrl());
                res.put("SPVersion","1.0.0");
                res.put("SpDownLoadFile","");
            }
        }catch (Exception e){
            logger.error("获取版本出错，错误原因:"+e);
            res.put("retCode",-1);
        }
        return res;
    }
}

