package com.dragon.datax.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.dto.DataxRDBMSConfigDto;
import com.dragon.datax.model.Task;
import com.dragon.datax.service.DtsService;
import com.dragon.datax.service.NodeService;
import com.dragon.datax.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TaskController
 * @Author pengl
 * @Date 2018/11/22 9:32
 * @Description Datax任务管理
 * @Version 1.0
 */
@RestController
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 列表
     *
     * @return
     */
    @PostMapping("/list")
    public Map<String, List<Task>> list() {
        List<Task> list = taskService.findAll();
        Map<String, List<Task>> resultMap = new HashMap<>(1);
        resultMap.put("data", list);
        return resultMap;
    }

    /**
     * 保存
     *
     * @param json
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@RequestBody String json) {
        DataxRDBMSConfigDto dataxRDBMSConfigDto = JSON.parseObject(json, new TypeReference<DataxRDBMSConfigDto>() {
        });
        if (StringUtils.isAnyBlank(dataxRDBMSConfigDto.getReader(), dataxRDBMSConfigDto.getWriter(),
                dataxRDBMSConfigDto.getReaderDsId(), dataxRDBMSConfigDto.getWriterDsId(), dataxRDBMSConfigDto.getWriteMode(),
                dataxRDBMSConfigDto.getWriterTableName(),
                dataxRDBMSConfigDto.getTaskSaveName())) {
            return new Result(false, -1, "缺少必要参数");
        }
        if (dataxRDBMSConfigDto.getWriterColumns().length == 0) {
            return new Result(false, -1, "参数验证失败");
        }
        if (StringUtils.isBlank(dataxRDBMSConfigDto.getQuerySql())) {
            if (StringUtils.isBlank(dataxRDBMSConfigDto.getReaderTableName())) {
                return new Result(false, -1, "参数验证失败");
            }
            if (dataxRDBMSConfigDto.getReaderColumns().length == 0) {
                return new Result(false, -1, "参数验证失败");
            }
        }
        return taskService.saveTask(dataxRDBMSConfigDto);
    }

    /**
     * 删除
     *
     * @param taskIds
     * @return
     */
    @PostMapping(value = "/del")
    public Result del(@RequestParam String taskIds) {
        return new Result(taskService.removeByIds(Arrays.asList(taskIds.split(","))));
    }

    /**
     * 任务执行
     *
     * @param taskIds
     * @return
     */
    @PostMapping(value = "/excute")
    @ResponseBody
    public Result excute(@RequestParam String taskIds, @RequestParam String nodeId) {
        return taskService.excute(taskIds, nodeId);
    }
}
