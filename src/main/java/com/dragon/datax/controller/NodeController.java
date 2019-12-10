package com.dragon.datax.controller;

import cn.hutool.core.util.IdUtil;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.model.Node;
import com.dragon.datax.service.NodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.hutool.crypto.symmetric.DES;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DtController
 * @Author pengl
 * @Date 2018/11/22 10:32
 * @Description 节点管理
 * @Version 1.0
 */
@RestController
@RequestMapping("node")
public class NodeController {
    @Autowired
    private NodeService nodeService;
    @Autowired
    private DES des;

    /**
     * 列表
     * @return
     */
    @PostMapping(value="/list")
    public Map<String, List<Node>> list(){
        List<Node> list =  nodeService.list();
        Map<String, List<Node>> resultMap = new HashMap<>(1);
        resultMap.put("data" , list);
        return resultMap;
    }

    /**
     * 保存
     * @param node
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@ModelAttribute Node node){
        //node.setPass(des.encryptHex(node.getPass()));
        if(StringUtils.isBlank(node.getId())){
            node.setId(IdUtil.fastSimpleUUID());
        }
        return new Result(nodeService.saveOrUpdate(node));
    }

    /**
     * 删除
     * @param nodeIds
     * @return
     */
    @PostMapping(value = "/del")
    public Result del(@RequestParam String nodeIds){
        return new Result(nodeService.removeByIds(Arrays.asList(nodeIds.split(","))));
    }

    /**
     * 节点测试
     * @param nodeId
     * @return
     */
    @PostMapping(value = "/test")
    public Result testNode(@RequestParam String nodeId){
        return nodeService.testNode(nodeId);
    }

    /**
     * 在线安装datax环境
     * TODO 没啥意义
     * @param nodeIds
     * @return
     */
    @PostMapping(value = "/install")
    public Result installDatax(@RequestParam String nodeIds){
        return null;
    }
}
