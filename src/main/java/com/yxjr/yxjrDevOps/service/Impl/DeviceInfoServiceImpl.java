package com.yxjr.yxjrDevOps.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.yxjrDevOps.dao.DeviceInfoDao;
import com.yxjr.yxjrDevOps.entity.DeviceInfo;
import com.yxjr.yxjrDevOps.service.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * (DeviceInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-05-11 14:09:02
 */
@Service("deviceInfoService")
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoDao, DeviceInfo> implements DeviceInfoService {

    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DeviceInfoDao deviceInfoDao;


    @Override
    public DeviceInfo queryById(Integer id) {
        return deviceInfoDao.queryById(id);
    }


    @Override
    public int insert(DeviceInfo deviceInfo) {
        deviceInfo.setVisitTime(new Date());
        deviceInfo.setCreateTime(new Date());
        return deviceInfoDao.insert(deviceInfo);
    }

    @Override
    public boolean update(DeviceInfo deviceInfo) {
        return deviceInfoDao.update(deviceInfo);
    }

    @Override
    public boolean deleteById(Integer id) {
        return deviceInfoDao.deleteById(id);
    }

    @Override
    public DeviceInfo getByDevId(String devId) {
        return deviceInfoDao.queryByDevId(devId);
    }

    @Override
    public boolean updateVisitTime(DeviceInfo deviceInfo) {
        return deviceInfoDao.updateVisitTime(deviceInfo);
    }
    @Override
    public String getVisitTime(String devId) {
        logger.info("设备["+devId+"]获取访问时间");
        try {
            DeviceInfo deviceInfo=deviceInfoDao.queryByDevId(devId);
            if(deviceInfo==null){
                logger.info("设备["+devId+"]未注册，返回访问时间为00000000");
                return "00000000";
            }else {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
                String strDate1 = sdf1.format(deviceInfo.getVisitTime());
                logger.info("设备["+devId+"]，返回访问时间为："+strDate1);
                return strDate1;
            }
        }catch (Exception e){
            logger.error("获取访问时间出现异常，原因："+e);
            return "00000000";
        }
    }
}

