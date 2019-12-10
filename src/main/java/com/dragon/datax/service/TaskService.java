package com.dragon.datax.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.dto.DataxRDBMSConfigDto;
import com.dragon.datax.model.Task;

import java.util.List;

/**
 * @ClassName TaskService
 * @Author pengl
 * @Date 2018/11/23 9:30
 * @Description 任务管理
 * @Version 1.0
 */
public interface TaskService extends IService<Task> {
    /**
     * @MethodName: findAll
     * @Author: pengl
     * @Date: 2019-12-09 14:46
     * @Description: 查询所有的任务
     * @Version: 1.0
     * @Param:
     * @Return:
     **/
    List<Task> findAll();

    /**
     * @MethodName: saveTask
     * @Author: pengl
     * @Date: 2019-12-09 14:55
     * @Description: 保存任务
     * @Version: 1.0
     * @Param:
     * @Return:
     **/
    Result saveTask(DataxRDBMSConfigDto dataxRDBMSConfigDto);

    /**
     * @MethodName: excute
     * @Author: pengl
     * @Date: 2019-12-09 15:11
     * @Description: 执行datax任务
     * @Version: 1.0
     * @Param:
     * @Return:
     **/
    Result excute(String taskIds, String nodeId);
}
