package com.dragon.datax.service.impl;

import cn.hutool.crypto.symmetric.DES;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.mapper.NodeMapper;
import com.dragon.datax.model.Node;
import com.dragon.datax.service.NodeService;
import com.dragon.datax.util.DataxConstant;
import com.dragon.datax.util.SFTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName NodeServiceImpl
 * @Author pengl
 * @Date 2019-12-09 13:09
 * @Description 节点管理业务层
 * @Version 1.0
 */
@Slf4j
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {
    @Autowired
    private DES des;

    @Override
    public Result testNode(String id) {
        Node node = this.getById(id);
        if (node == null) {
            return new Result(false, -1, "节点不存在！");
        }
        SFTPUtil sftpUtil = null;
        try {
            sftpUtil = new SFTPUtil(node.getIp(), node.getUser(), node.getPass(), node.getPort());
            sftpUtil.connect(DataxConstant.SSH_CON_TIMEOUT);
        } catch (Exception e) {
            log.error("====>>>Datax节点连接失败：{}", e.getMessage(), e);
            return new Result(false, -1, "节点连接失败：" + e.getMessage());
        } finally {
            if (sftpUtil != null) {
                sftpUtil.close();
            }
        }
        return new Result();
    }
}
