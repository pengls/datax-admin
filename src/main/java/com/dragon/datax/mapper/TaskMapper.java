package com.dragon.datax.mapper;

import com.iflytek.digitalpark.datacenter.datax.model.TaskModel;

import java.util.List;

/**
 * @ClassName TaskMapper
 * @Author pengl
 * @Date 2018/11/23 9:30
 * @Description TODO
 * @Version 1.0
 */
public interface TaskMapper {
    /**
     * 查询任务列表
     * @return
     */
    List<TaskModel> findAll();

    /**
     * 插入任务
     * @param taskModel
     * @return
     */
    int insertTask(TaskModel taskModel);

    /**
     * 删除任务
     * @param id
     * @return
     */
    int delTaskById(String id);

    /**
     * 更新任务
     * @param taskModel
     * @return
     */
    int updateTaskById(TaskModel taskModel);

    /**
     * 通过ID查询任务
     * @param taskId
     * @return
     */
    TaskModel findById(String taskId);
}
