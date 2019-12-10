package com.dragon.datax.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.model.Node;

/**
 * @ClassName DtsService
 * @Author pengl
 * @Date 2018/11/22 10:55
 * @Description 节点管理
 * @Version 1.0
 */
public interface NodeService extends IService<Node> {
    /**
     * @MethodName: testNode
     * @Author: pengl
     * @Date: 2019-12-09 14:39
     * @Description: 节点测试
     * @Version: 1.0
     * @Param:
     * @Return:
     **/
    Result testNode(String nodeId);
}
