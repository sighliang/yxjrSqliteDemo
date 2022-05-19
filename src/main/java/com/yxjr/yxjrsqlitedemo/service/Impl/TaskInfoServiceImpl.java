package com.yxjr.yxjrsqlitedemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.yxjrsqlitedemo.dao.TaskInfoDao;
import com.yxjr.yxjrsqlitedemo.entity.DeviceInfo;
import com.yxjr.yxjrsqlitedemo.entity.SelectVo;
import com.yxjr.yxjrsqlitedemo.entity.TaskInfo;
import com.yxjr.yxjrsqlitedemo.entity.TaskInfoVO;
import com.yxjr.yxjrsqlitedemo.service.DeviceInfoService;
import com.yxjr.yxjrsqlitedemo.service.TaskInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * (TaskInfo)表服务实现类
 *
 * @author makejava
 * @since 2022-05-07 14:12:52
 */
@Service("taskInfoService")
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoDao, TaskInfo> implements TaskInfoService {

    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DeviceInfoService deviceInfoService;

    @Autowired
    TaskInfoDao taskInfoDao;

    @Override
    public boolean saveTaskInfo(TaskInfoVO taskInfoVO) {
        try{
            logger.info("开始保存上传的任务数据");
            String devId=taskInfoVO.getDevId();
            //自动注册逻辑，查询设备编号是否存在，若不存在，注册，若存在，更新访问时间
            DeviceInfo deviceInfo = deviceInfoService.getByDevId(devId);
            if(deviceInfo == null){
                deviceInfo = new DeviceInfo();
                deviceInfo.setDevId(devId);
                deviceInfo.setBranchId(taskInfoVO.getBranchId());
                deviceInfo.setBranchName(taskInfoVO.getBranchName());
                deviceInfo.setCreateTime(new Date());
                deviceInfo.setVisitTime(new Date());
                deviceInfo.setIp(taskInfoVO.getIp());
                deviceInfoService.insert(deviceInfo);
            }else {
                Date lastVisitTime=deviceInfo.getVisitTime();
                Date nowTime=new Date();
                if(nowTime.getTime()-lastVisitTime.getTime()<24*60*60*1000){
                    return false;
                }
                deviceInfo.setVisitTime(new Date());
                deviceInfoService.updateVisitTime(deviceInfo);
            }
            List<TaskInfo> taskInfoList = taskInfoVO.getData();
            if(taskInfoList != null){
                for(TaskInfo taskInfo:taskInfoList){
                    taskInfo.setDevId(taskInfoVO.getDevId());
                    this.save(taskInfo);
                }
            }
            logger.info("保存上传的任务数据成功");
            return true;
        }catch (Exception e){
            logger.info("开始保存上传的任务失败，错误原因"+e);
            return false;
        }
    }

    @Override
    public int count(SelectVo selectVo) {
        return taskInfoDao.count(selectVo);
    }
}

