package com.yxjr.yxjrsqlitedemo.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.yxjrsqlitedemo.common.UploadConfig;
import com.yxjr.yxjrsqlitedemo.dao.LogPackageDao;
import com.yxjr.yxjrsqlitedemo.entity.LogPackage;
import com.yxjr.yxjrsqlitedemo.service.LogPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (LogPackage)表服务实现类
 *
 * @author makejava
 * @since 2022-05-09 11:03:25
 */
@Service("logPackageService")
public class LogPackageServiceImpl extends ServiceImpl<LogPackageDao, LogPackage> implements LogPackageService {


    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UploadConfig uploadConfig;

    @Autowired
    LogPackageDao logPackageDao;

    @Override
    public boolean saveInfo(String logName, String devId) {
        try {
            logger.info("保存上传的日志包数据");
            LogPackage logPackage = new LogPackage();
            logPackage.setLogName(logName);
            logPackage.setUploadTime(new Date());
            logPackage.setDevId(devId);
            InetAddress ip4 = Inet4Address.getLocalHost();
            String url="http://"+ip4.getHostAddress()+":"+uploadConfig.getPort()+"/common/downloadLog/"+devId+"/"+logName;
            logPackage.setUrl(url);
            return this.save(logPackage);
        }catch (Exception e){
            logger.error("保存上传日志数据出现异常，异常原因"+e);
            return false;
        }
    }

    @Override
    public List<LogPackage> queryByDevId(String devId) {
        return logPackageDao.queryByDevId(devId);
    }

    @Override
    public boolean deleteLog(){
        Date today = new Date();
        Calendar cal=Calendar.getInstance();
        try{
            File ifile=new File(uploadConfig.getLogPath());
            for(File file:ifile.listFiles()){
                if(file.isDirectory()) {
                    for (File file1 : file.listFiles()) {
                        long time = file1.lastModified();

                        cal.setTimeInMillis(time);

                        Date lastModified = cal.getTime();

                        long days = getDistDates(today, lastModified);

                        if (days >= 30) {
                            file1.delete();
                        }
                    }
                }
            }
            logPackageDao.deleteByTime();
            logger.info("删除服务器一个月前上传的日志成功");
            return true;
        }catch (Exception e){
            logger.error("删除服务器之前的日志文件异常"+e);
            return false;
        }
    }
    public static long getDistDates(Date startDate,Date endDate) {

        long totalDate = 0;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);

        long timestart = calendar.getTimeInMillis();

        calendar.setTime(endDate);

        long timeend = calendar.getTimeInMillis();

        totalDate = Math.abs((timeend - timestart))/(1000*60*60*24);

        return totalDate;

    }
}

