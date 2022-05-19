package com.yxjr.yxjrsqlitedemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yxjr.yxjrsqlitedemo.entity.SelectVo;
import com.yxjr.yxjrsqlitedemo.entity.TaskInfo;
import com.yxjr.yxjrsqlitedemo.service.TaskInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (TaskInfo)表控制层
 *
 * @author makejava
 * @since 2022-05-07 14:12:51
 */
@RestController
@RequestMapping("taskInfo")
public class TaskInfoController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private TaskInfoService taskInfoService;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param taskInfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<TaskInfo> page, TaskInfo taskInfo) {
        return success(this.taskInfoService.page(page, new QueryWrapper<>(taskInfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.taskInfoService.getById(id));
    }



    /**
     * 修改数据
     *
     * @param taskInfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody TaskInfo taskInfo) {
        return success(this.taskInfoService.updateById(taskInfo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.taskInfoService.removeByIds(idList));
    }

    @PostMapping("/count")
    public R Count(@RequestBody SelectVo selectVo) {
        return success(taskInfoService.count(selectVo));
    }


}

