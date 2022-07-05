package com.yxjr.yxjrDevOps.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxjr.yxjrDevOps.entity.SelectVo;
import com.yxjr.yxjrDevOps.entity.TaskInfo;
import com.yxjr.yxjrDevOps.entity.TaskInfoVO;

/**
 * (TaskInfo)表服务接口
 *
 * @author makejava
 * @since 2022-05-07 14:12:52
 */
public interface TaskInfoService extends IService<TaskInfo> {
    boolean saveTaskInfo(TaskInfoVO taskInfoVO);

    int count(SelectVo selectVo);
}

