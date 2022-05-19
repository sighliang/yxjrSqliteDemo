package com.yxjr.yxjrsqlitedemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yxjr.yxjrsqlitedemo.common.UploadConfig;
import com.yxjr.yxjrsqlitedemo.entity.DeviceInfo;
import com.yxjr.yxjrsqlitedemo.service.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * (DeviceInfo)表控制层
 *
 * @author makejava
 * @since 2022-05-11 14:07:00
 */
@Controller
@RequestMapping("deviceInfo")
public class DeviceInfoController extends ApiController {
    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String prefix="Device/";
    /**
     * 服务对象
     */
    @Resource
    private DeviceInfoService deviceInfoService;

    @Autowired
    private UploadConfig uploadConfig;


    /***
     * 下载数据库
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/downloadDB")
    @ResponseBody
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("开始下发数据库");
        String FullPath =  uploadConfig.getDbPath();
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
                logger.info("下载数据库成功");
            } catch (Exception e) {
                logger.error("下载数据库失败"+e);
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
            logger.error("下载数据库失败");
            String s = "{\"code\":-1,\"msg\":\"文件不存在\"}";
            return s;
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param deviceInfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ResponseBody
    public R selectAll(Page<DeviceInfo> page, DeviceInfo deviceInfo) {
        return success(this.deviceInfoService.page(page, new QueryWrapper<>(deviceInfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ResponseBody
    public R selectOne(@PathVariable Serializable id) {
        return success(this.deviceInfoService.getById(id));
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return prefix+"add";
    }

    /**
     * 上传更新包
     * @return
     */
    @RequestMapping("/upload")
    public String upload(){
        return prefix+"uploadPackage";
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("deviceInfo",deviceInfoService.getById(id));
        return prefix+"edit";
    }


    /**
     * 新增数据
     *
     * @param deviceInfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ResponseBody
    public R insert(DeviceInfo deviceInfo) {
        return success(deviceInfoService.insert(deviceInfo));
    }


    /**
     * 修改数据
     *
     * @param deviceInfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ResponseBody
    public R update(DeviceInfo deviceInfo) {
        return success(deviceInfoService.update(deviceInfo));
    }

    /**
     * 删除数据
     *
     * @param  id
     * @return 删除结果
     */
    @DeleteMapping
    @ResponseBody
    public R deleteById(Integer id) {
        return success(deviceInfoService.deleteById(id));
    }
}

