package com.dragon.datax.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.iflytek.digitalpark.commons.vo.BaseResult;
import com.iflytek.digitalpark.datacenter.datax.model.DataSourceModel;
import com.iflytek.digitalpark.datacenter.datax.model.DataxRDBMSConfigModel;
import com.iflytek.digitalpark.datacenter.datax.model.NodeModel;
import com.iflytek.digitalpark.datacenter.datax.model.TaskModel;
import com.iflytek.digitalpark.datacenter.datax.service.DsService;
import com.iflytek.digitalpark.datacenter.datax.service.NodeService;
import com.iflytek.digitalpark.datacenter.datax.service.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
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
@Controller
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private DsService dsService;
    @Autowired
    private NodeService nodeService;

    @RequestMapping("")
    public String index(Model model, HttpSession session){
        /**
         * 获取数据源列表
         */
        List<DataSourceModel> dtslist = dsService.findAll();
        model.addAttribute("dtslist", dtslist);
        /**
         * 获取节点列表
         */
        List<NodeModel> nodeList = nodeService.findAll();
        model.addAttribute("nodelist", nodeList);

        return "task/task.index";
    }

    /**
     * 列表
     * @return
     */
    @PostMapping(value="/list")
    @ResponseBody
    public Map<String, List<TaskModel>> list(){
        List<TaskModel> list =  taskService.findAll();
        Map<String, List<TaskModel>> resultMap = new HashMap<>(1);
        resultMap.put("data" , list);
        return resultMap;
    }

    /**
     * 保存
     * @param json
     * @return
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public BaseResult save(@RequestBody String json){
        DataxRDBMSConfigModel model = JSON.parseObject(json, new TypeReference<DataxRDBMSConfigModel>(){});
        if(StringUtils.isAnyBlank(model.getReader(), model.getWriter(),
                model.getReaderDsId(),model.getWriterDsId(),model.getWriteMode(),
                model.getWriterTableName(),
                model.getTaskSaveName())){
            return new BaseResult(false, "-1", "参数验证失败");
        }
        if(model.getWriterColumns().length == 0){
            return new BaseResult(false, "-1", "参数验证失败");
        }
        if(StringUtils.isBlank(model.getQuerySql())){
            if(StringUtils.isBlank(model.getReaderTableName())){
                return new BaseResult(false, "-1", "参数验证失败");
            }
            if(model.getReaderColumns().length == 0){
                return new BaseResult(false, "-1", "参数验证失败");
            }
        }
        return taskService.save(model);
    }

    /**
     * 删除
     * @param taskIds
     * @return
     */
    @PostMapping(value = "/del")
    @ResponseBody
    public BaseResult del(@RequestParam String taskIds){
        return taskService.del(taskIds);
    }

    /**
     * 任务执行
     * @param taskIds
     * @return
     */
    @PostMapping(value = "/excute")
    @ResponseBody
    public BaseResult excute(@RequestParam String taskIds, @RequestParam String nodeId){
        return taskService.excute(taskIds, nodeId);
    }
}
