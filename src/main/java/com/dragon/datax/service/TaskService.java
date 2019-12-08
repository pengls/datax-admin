package com.dragon.datax.service;

import com.alibaba.fastjson.JSON;
import com.iflytek.digitalpark.commons.utils.AES;
import com.iflytek.digitalpark.commons.utils.taskcenter.model.DataOperResult;
import com.iflytek.digitalpark.commons.vo.BaseResult;
import com.iflytek.digitalpark.commons.vo.ObjectResult;
import com.iflytek.digitalpark.datacenter.datax.mapper.NodeMapper;
import com.iflytek.digitalpark.datacenter.datax.mapper.TaskMapper;
import com.iflytek.digitalpark.datacenter.datax.model.DataxRDBMSConfigModel;
import com.iflytek.digitalpark.datacenter.datax.model.NodeModel;
import com.iflytek.digitalpark.datacenter.datax.model.TaskModel;
import com.iflytek.digitalpark.datacenter.datax.util.DataxTemplateUtil;
import com.iflytek.digitalpark.datacenter.datax.util.SFTPUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TaskService
 * @Author pengl
 * @Date 2018/11/23 9:30
 * @Description TODO
 * @Version 1.0
 */
@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private DataxTemplateUtil dataxTemplateUtil;
    @Autowired
    private NodeMapper nodeMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    /**
     *
     * @return
     */
    public List<TaskModel> findAll(){
        return taskMapper.findAll();
    }

    /**
     * 保存任务
     * @param model
     * @return
     */
    public BaseResult save(DataxRDBMSConfigModel model) {
        String content = dataxTemplateUtil.getRDBMSTemplate(model);
        model.setTaskContent(content);
        TaskModel taskModel = new TaskModel();
        taskModel.setTaskName(model.getTaskName());
        taskModel.setTaskDesc(model.getTaskDesc());
        taskModel.setReaderDsId(model.getReaderDsId());
        taskModel.setWriterDsId(model.getWriterDsId());
        taskModel.setTaskContent(content);
        taskModel.setStatus(model.getStatus());
        taskModel.setTaskId(model.getTaskId());
        taskModel.setTaskSaveName(model.getTaskSaveName());
        /**
         * 保存任务
         */
        int i = 0 ;
        if(StringUtils.isBlank(model.getTaskId())){
            taskModel.setCreateTime(new Date());
            i = taskMapper.insertTask(taskModel);
        }else{
            taskModel.setUpdateTime(new Date());
            i = taskMapper.updateTaskById(taskModel);
        }
        if(i == 0){
            return new BaseResult(false, "-1", "任务保存失败");
        }
        /**
         * 远程推送任务
         */
        List<String> pns = pushNodes(model.getPushNodes(), model);
        ObjectResult obr = new ObjectResult(true, "0");
        obr.setData(pns);
        return obr;
    }

    /**
     * 远程推送任务文件
     * @param nodes
     * @param model
     * @return
     */
    private List<String> pushNodes(String[] nodes, DataxRDBMSConfigModel model){
        LOGGER.info("推送节点：" + JSON.toJSONString(nodes));
        int lgt;
        if(nodes == null || (lgt=nodes.length) == 0){
            return new ArrayList<>(0);
        }
        List<String> sucNodes = new ArrayList<>(lgt);
        for(String nodeId : nodes){
            NodeModel nodeModel = nodeMapper.findById(nodeId);
            if(nodeModel == null){
                continue;
            }
            SFTPUtil sftpUtil = null;
            try {
                String nodeDefaultPath = nodeModel.getNodeDefaultPath();
                if(nodeDefaultPath.endsWith("/")){
                    nodeDefaultPath = nodeDefaultPath.substring(0, nodeDefaultPath.length() - 1);
                }
                String remotePath = nodeDefaultPath + "/" + model.getTaskSaveName() + ".json";
                sftpUtil = new SFTPUtil(nodeModel.getNodeIp(), nodeModel.getNodeLoginUser(),
                        AES.decrypt(nodeModel.getNodeLoginPass(),AES.findKey()), nodeModel.getNodePort());
                sftpUtil.connect();
                sftpUtil.uploadText(remotePath, model.getTaskContent());
                sftpUtil.close();
                sucNodes.add(nodeModel.getNodeIp() + "--->" + remotePath);
            }catch (Exception e){
                LOGGER.error("远程推送任务到Datax服务节点失败：{}", e.getMessage(), e);
            }finally {
                if(sftpUtil != null){
                    sftpUtil.close();
                }
            }


        }
        return sucNodes;
    }

    /**
     * 删除任务
     * @param taskIds
     * @return
     */
    public BaseResult del(String taskIds) {
        String[] usrid = taskIds.split(",");
        int i = 0;
        for(String id : usrid){
            i = i + taskMapper.delTaskById(id);
        }
        return new BaseResult(true, "0", i + "");
    }

    /**
     * 任务执行
     * @param taskIds
     * @return
     */
    public BaseResult excute(String taskIds, String nodeId) {
        NodeModel nodeModel = nodeMapper.findById(nodeId);
        if(nodeModel == null){
            return new BaseResult(false , "-1", "执行节点不存在");
        }
        String[] taskid = taskIds.split(",");
        List<String> sucTasks = new ArrayList<>(taskid.length);
        ObjectResult objectResult = new ObjectResult(true, "0");
        int i = 0;
        for(String id : taskid){
            TaskModel taskModel = taskMapper.findById(id);
            if(taskModel == null){
                continue;
            }
            SFTPUtil sftpUtil = null;
            try {
                sftpUtil = new SFTPUtil(nodeModel.getNodeIp(), nodeModel.getNodeLoginUser(),
                        AES.decrypt(nodeModel.getNodeLoginPass(),AES.findKey()), nodeModel.getNodePort());
                sftpUtil.connect();
                DataOperResult dataOperResult = sftpUtil.execCmd(
                        "python " + nodeModel.getNodeDataxPath() + "/bin/datax.py " +  nodeModel.getNodeDefaultPath() + "/" + taskModel.getTaskSaveName() + ".json");
                i++;
                sucTasks.add(taskModel.getTaskName() + "--->" + dataOperResult.getOperResultDesc());
            }catch (Exception e){
                LOGGER.error("远程推送任务到Datax服务节点失败：{}", e.getMessage(), e);
            }finally {
                if(sftpUtil != null){
                    sftpUtil.close();
                }
            }
        }
        objectResult.setMsg(i + "");
        objectResult.setData(sucTasks);
        return objectResult;
    }
}
