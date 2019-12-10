package com.dragon.datax.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.symmetric.DES;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.dto.DataOperResultDto;
import com.dragon.datax.dto.DataxRDBMSConfigDto;
import com.dragon.datax.mapper.TaskMapper;
import com.dragon.datax.model.Node;
import com.dragon.datax.model.Task;
import com.dragon.datax.service.NodeService;
import com.dragon.datax.service.TaskService;
import com.dragon.datax.util.DataxConstant;
import com.dragon.datax.util.DataxTemplateUtil;
import com.dragon.datax.util.SFTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TaskServiceImpl
 * @Author pengl
 * @Date 2019-12-09 13:09
 * @Description 任务管理业务层
 * @Version 1.0
 */
@Slf4j
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private NodeService nodeService;
    @Autowired
    private DES des;

    @Autowired
    private DataxTemplateUtil dataxTemplateUtil;

    @Override
    public List<Task> findAll() {
        return taskMapper.findAll();
    }

    @Override
    public Result saveTask(DataxRDBMSConfigDto dto) {
        String content = dataxTemplateUtil.getRDBMSTemplate(dto);
        dto.setTaskContent(content);

        Task task = Task.builder().name(dto.getTaskName()).remark(dto.getTaskDesc()).readerDsId(dto.getReaderDsId())
                .writerDsId(dto.getWriterDsId()).content(dto.getTaskContent()).status(dto.getStatus()).id(dto.getTaskId())
                .taskSaveName(dto.getTaskSaveName()).build();

        /**
         * 保存任务
         */
        if (StringUtils.isBlank(task.getId())) {
            task.setId(IdUtil.fastSimpleUUID());
            task.setCreateTime(new Date());
        }

        task.setUpdateTime(new Date());

        if (!this.saveOrUpdate(task)) {
            return new Result(false, -1, "任务保存失败");
        }

        /**
         * 远程推送任务
         */
        List<String> pns = pushNodes(dto);
        return new Result(pns);
    }

    @Override
    public Result excute(String taskIds, String nodeId) {
        Node nodeModel = nodeService.getById(nodeId);
        if (nodeModel == null) {
            return new Result(false, -1, "执行节点不存在");
        }
        String[] taskid = taskIds.split(",");
        List<String> sucTasks = new ArrayList<>(taskid.length);
        int i = 0;
        for (String id : taskid) {
            Task taskModel = this.getById(id);
            if (taskModel == null) {
                continue;
            }
            SFTPUtil sftpUtil = null;
            try {
                sftpUtil = new SFTPUtil(nodeModel.getIp(), nodeModel.getUser(), nodeModel.getPass(), nodeModel.getPort());
                sftpUtil.connect(DataxConstant.SSH_CON_TIMEOUT);
                DataOperResultDto dataOperResult = sftpUtil.execCmd(
                        "python " + nodeModel.getDataxPath() + "/bin/datax.py " + nodeModel.getDefaultPath() + "/" + taskModel.getTaskSaveName() + ".json");
                i++;
                sucTasks.add(taskModel.getName() + "--->" + dataOperResult.getOperResultDesc());
            } catch (Exception e) {
                log.error("远程推送任务到Datax服务节点失败：{}", e.getMessage(), e);
            } finally {
                if (sftpUtil != null) {
                    sftpUtil.close();
                }
            }
        }
        return new Result(sucTasks);
    }

    /**
     * 远程推送任务文件
     *
     * @param model
     * @return
     */
    private List<String> pushNodes(DataxRDBMSConfigDto model) {
        String[] nodes = model.getPushNodes();
        log.info("推送节点：{}", JSON.toJSONString(nodes));
        int lgt;
        if (nodes == null || (lgt = nodes.length) == 0) {
            return new ArrayList<>(0);
        }
        List<String> sucNodes = new ArrayList<>(lgt);
        for (String nodeId : nodes) {
            Node nodeModel = nodeService.getById(nodeId);
            if (nodeModel == null) {
                continue;
            }
            SFTPUtil sftpUtil = null;
            try {
                String nodeDefaultPath = nodeModel.getDefaultPath();
                if (nodeDefaultPath.endsWith("/")) {
                    nodeDefaultPath = nodeDefaultPath.substring(0, nodeDefaultPath.length() - 1);
                }
                String remotePath = nodeDefaultPath + "/" + model.getTaskSaveName() + ".json";
                sftpUtil = new SFTPUtil(nodeModel.getIp(), nodeModel.getUser(), nodeModel.getPass(), nodeModel.getPort());
                sftpUtil.connect(DataxConstant.SSH_CON_TIMEOUT);
                sftpUtil.uploadText(remotePath, model.getTaskContent());
                sftpUtil.close();
                sucNodes.add(nodeModel.getIp() + "--->" + remotePath);
            } catch (Exception e) {
                log.error("远程推送任务到Datax服务节点失败：{}", e.getMessage(), e);
            } finally {
                if (sftpUtil != null) {
                    sftpUtil.close();
                }
            }
        }
        return sucNodes;
    }
}
