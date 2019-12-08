package com.dragon.datax.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DsService
 * @Author pengl
 * @Date 2018/11/22 10:55
 * @Description 节点管理
 * @Version 1.0
 */
@Service
public class NodeService {
    @Autowired
    private NodeMapper nodeMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(NodeService.class);

    /**
     * 查询所有节点
     * @return
     */
    public List<NodeModel> findAll(){
        return nodeMapper.findAll();
    }

    /**
     * 通过ID查询节点
     * @return
     */
    public NodeModel findById(String nodeId){
        return nodeMapper.findById(nodeId);
    }

    /**
     * 保存节点
     * @param nodeModel
     * @return
     */
    public BaseResult save(NodeModel nodeModel) {
        String uid = nodeModel.getNodeId();
        try {
            nodeModel.setNodeLoginPass(AES.encrypt(nodeModel.getNodeLoginPass(), AES.findKey()));
        }catch (Exception e){
            e.printStackTrace();
        }
        int i = 0;
        if(StringUtils.isBlank(uid)){
            i = nodeMapper.insertNode(nodeModel);
        }else{
            i = nodeMapper.updateNodeById(nodeModel);
        }

        if(i > 0){
            return new BaseResult(true, "0" , nodeModel.getNodeId());
        }
        return new BaseResult(false, "-1", "操作失败");
    }

    /**
     * 删除节点
     * @param nodeIds
     * @return
     */
    public BaseResult del(String nodeIds) {
        String[] usrid = nodeIds.split(",");
        int i = 0;
        for(String id : usrid){
            i = i + nodeMapper.delNodeById(id);
        }
        return new BaseResult(true, "0", i + "");
    }

    /**
     * 节点测试
     * @param nodeId
     * @return
     */
    public BaseResult testNode(String nodeId) {
        NodeModel nodeModel = nodeMapper.findById(nodeId);
        if(nodeModel == null){
            return new BaseResult(false, "-1" , "节点不存在！");
        }
        SFTPUtil sftpUtil = null;
        try {
            sftpUtil = new SFTPUtil(nodeModel.getNodeIp(),nodeModel.getNodeLoginUser(),
                    AES.decrypt(nodeModel.getNodeLoginPass(),AES.findKey()), nodeModel.getNodePort());
            sftpUtil.connect();
        } catch (Exception e) {
            LOGGER.error("====>>>Datax节点连接失败：{}", e.getMessage(), e);
            return new BaseResult(false , "-1", "节点连接失败");
        } finally {
            if(sftpUtil != null){
                sftpUtil.close();
            }
        }
        return new BaseResult();

    }
}
