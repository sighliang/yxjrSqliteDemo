package com.yxjr.yxjrDevOps.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yxjr.yxjrDevOps.common.UploadConfig;
import com.yxjr.yxjrDevOps.entity.UploadPackage;
import com.yxjr.yxjrDevOps.service.UploadPackageService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.File;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * (UploadPackage)表控制层
 *
 * @author makejava
 * @since 2022-05-09 11:04:16
 */
@Controller
@RequestMapping("uploadPackage")
public class UploadPackageController extends ApiController {

    //日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UploadConfig uploadConfig;

    /**
     * 服务对象
     */
    @Resource
    private UploadPackageService uploadPackageService;


    /**
     * 添加
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "UploadPackage/add";
    }

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param uploadPackage 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ResponseBody
    public R selectAll(Page<UploadPackage> page, UploadPackage uploadPackage) {
        return success(this.uploadPackageService.page(page, new QueryWrapper<>(uploadPackage)));
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
        return success(this.uploadPackageService.getById(id));
    }

    /**
     * 新增更新包数据
     *
     * @param
     * @return 新增结果
     */
    @PostMapping
    @ResponseBody
    public R insert(MultipartHttpServletRequest request) {
        try{
            int  isAll = Integer.parseInt(request.getParameter("isAll"));
            MultipartFile file=  request.getFile("file");
            String devId = request.getParameter("devIds");
            String version = request.getParameter("version");
            String[] devIds =devId.split(",");
            if(isAll !=0 && isAll !=1){
                logger.error("传入参数错误，无法判断是否为全量或局部更新包:isAll=["+isAll+"]");
                return R.failed("传入参数错误，无法判断是否为全量或局部更新包:isAll=["+isAll+"]");
            }
            //匹配版本的文件格式
            String pattern = "[0-9]+\\.[0-9]+\\.[0-9]+";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(version);
            if( !m.matches()){
                logger.error("版本名的格式不对，版本：version：["+version+"]");
                return R.failed("版本名的格式不对，版本：version：["+version+"]");
            }

            String packPath = uploadConfig.getUploadpacketPath();
            String fileName=file.getOriginalFilename();//文件名
            String pathString = null;
            if(file!=null){
                pathString = packPath+"/" + fileName;//new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" +
            }else {
                return R.failed("文件为空");
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
            return success(uploadPackageService.insertBy(isAll,devIds,fileName,version));
        }catch (Exception e){
            logger.error("异常原因为："+e);
            return R.failed("错误原因"+e);
        }
    }

    /**
     * 修改数据
     *
     * @param uploadPackage 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ResponseBody
    public R update(@RequestBody UploadPackage uploadPackage) {
        return success(this.uploadPackageService.updateById(uploadPackage));
    }

    /**
     * 锁定设备版本
     * @param id,devId
     * @return
     */
    @PostMapping("/lock")
    @ResponseBody
    public R lock(@RequestParam("id") int id,@RequestParam("devId") String devId){
        return uploadPackageService.lock(id,devId);
    }

    /**
     * 解锁设备版本
     * @param devId
     * @return
     */
    @PostMapping("/unlock")
    @ResponseBody
    public R unlock(@RequestParam("id") int id,@RequestParam("devId")String devId){
        return uploadPackageService.unlock(id,devId);
    }


    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping
    @ResponseBody
    public R deleteById(int id) {
        UploadPackage uploadPackage = this.uploadPackageService.getById(id);
        uploadPackageService.deleteFile(uploadPackage.getPackageName());
        return success(this.uploadPackageService.removeById(id));
    }



}

