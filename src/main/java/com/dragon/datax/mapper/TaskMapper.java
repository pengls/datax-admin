package com.dragon.datax.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dragon.datax.model.Task;
import java.util.List;

/**
 * @ClassName TaskMapper
 * @Author pengl
 * @Date 2018/11/23 9:30
 * @Description dao层
 * @Version 1.0
 */
public interface TaskMapper extends BaseMapper<Task> {

    List<Task> findAll();
}
