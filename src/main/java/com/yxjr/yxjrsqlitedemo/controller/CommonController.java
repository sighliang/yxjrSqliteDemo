package com.yxjr.yxjrsqlitedemo.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.yxjr.yxjrsqlitedemo.common.UploadConfig;
import com.yxjr.yxjrsqlitedemo.entity.TaskInfoVO;
import com.yxjr.yxjrsqlitedemo.service.DeviceInfoService;
import com.yxjr.yxjrsqlitedemo.service.LogPackageService;
import com.yxjr.yxjrsqlitedemo.service.TaskInfoService;
import com.yxjr.yxjrsqlitedemo.service.UploadPackageService;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 对前端设备端普适性接口
 */
@RestController
@RequestMapping("/common")
public class CommonController extends ApiController {
    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UploadConfig uploadConfig;
    @Autowired
    private UploadPackageService uploadPackageService;
    @Autowired
    private LogPackageService logPackageService;
    @Autowired
    private TaskInfoService taskInfoService;
    @Autowired
    private DeviceInfoService deviceInfoService;


    /***
     * 下载更新包
     * @param request
     * @param response
     * @param fileFullName
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/download/{fileFullName}")
    @ResponseBody
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response, @PathVariable(name = "fileFullName")String fileFullName) throws UnsupportedEncodingException {
        logger.info("设备开始下载更新包");
        String FullPath =  uploadConfig.getUploadpacketPath()+fileFullName;
        File packetFile = new File(FullPath);
        String fileName = packetFile.getName(); //下载的文件名
        File file = new File(FullPath);
        // 如果文件名存在，则进行下载
        if (file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                logger.info("下载更新包成功");
            } catch (Exception e) {
                logger.error("下载更新包失败"+e);
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        } else {//对应文件不存在
            logger.error("设备想要下载的更新包不存在");
            String s = "{\"code\":-1,\"msg\":\"文件不存在\"}";
            return s;
        }
    }
    /***
     * 下载日志包
     * @param request
     * @param response
     * @param fileFullName
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/downloadLog/{devId}/{fileFullName}")
    @ResponseBody
    public String downloadLogFile(HttpServletRequest request,
                               HttpServletResponse response,@PathVariable(name = "devId")String devId ,@PathVariable(name = "fileFullName")String fileFullName) throws UnsupportedEncodingException {
        logger.info("设备开始下载更新包");
        String FullPath =  uploadConfig.getLogPath()+devId+"/"+fileFullName;
        File packetFile = new File(FullPath);
        String fileName = packetFile.getName(); //下载的文件名
        // 如果文件名存在，则进行下载
        if (packetFile.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(packetFile);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                logger.info("下载日志包成功");
            } catch (Exception e) {
                logger.error("下载日志包失败"+e);
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        } else {//对应文件不存在
            logger.error("设备想要下载的日志包不存在");
            String s = "{\"code\":-1,\"msg\":\"文件不存在\"}";
            return s;
        }
    }

    /**
     * 获取版本号
     * @param devId
     * @return
     */
    @GetMapping("/queryVersion/{devId}")
    public JSONObject queryVersion(@PathVariable("devId") String devId){
        logger.info("设备获取版本号");
        return uploadPackageService.queryVersion(devId);
    }
    /**
     * 上传日志包
     *
     * @param
     * @return 新增结果
     */
    @PostMapping("/uploadLog")
    @ResponseBody
    public R uploadLog(MultipartHttpServletRequest request) {
        try {
            logger.info("设备开始上传日志包");
             String devId=request.getParameter("devId");
            if(devId == null || devId == ""){
                return failed("未获取到设备编号");
            }
            MultipartFile file=request.getFile("file");
            String packPath = uploadConfig.getLogPath()+devId;
           String fileName=file.getOriginalFilename();//文件名
            String pathString = null;
            if(file!=null){
                pathString = packPath+"/" + fileName;//new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +
            }else {
                return failed("文件为空");
            }
            //包创建
            File pack=new File(packPath);
            if(!pack.getParentFile().exists()){
                pack.getParentFile().mkdirs();
            }
            //文件创建
            File files=new File(pathString);
            //打印查看上传路径
            logger.info("pathString为："+pathString);
            if(!files.getParentFile().exists()){
                files.getParentFile().mkdirs();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), files);
            logger.info("设备日志上传成功");
            return success(logPackageService.saveInfo(fileName,devId));
        }catch (Exception e){
            logger.error("上传日志压缩包出现异常"+e);
            return R.failed("运行出现异常，异常原因为：["+e+"]");
        }
    }


    /**
     * 新增任务数据
     *
     * @param taskInfoVO 实体对象
     * @return 新增结果
     */
    @PostMapping("/taskInfo")
    public R insert(@RequestBody TaskInfoVO taskInfoVO) {
        logger.info("设备提交本地任务列表");
        return success(taskInfoService.saveTaskInfo(taskInfoVO));
    }

    @GetMapping("/selectTime")
    public R selectTime(String devId) {
        logger.info("设备获取设备上传时间");
        return success(deviceInfoService.getVisitTime(devId));
    }
}
