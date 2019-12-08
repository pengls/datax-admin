package com.dragon.datax.controller;

import com.iflytek.digitalpark.commons.vo.BaseResult;
import com.iflytek.digitalpark.datacenter.datax.model.NodeModel;
import com.iflytek.digitalpark.datacenter.datax.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
@Controller
@RequestMapping("node")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    /**
     * 首页
     * @return
     */
    @RequestMapping("")
    public String index(){
        return "node/node.index";
    }

    /**
     * 列表
     * @return
     */
    @PostMapping(value="/list")
    @ResponseBody
    public Map<String, List<NodeModel>> list(){
        List<NodeModel> list =  nodeService.findAll();
        Map<String, List<NodeModel>> resultMap = new HashMap<>(1);
        resultMap.put("data" , list);
        return resultMap;
    }

    /**
     * 保存
     * @param nodeModel
     * @return
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public BaseResult save(@ModelAttribute NodeModel nodeModel){
        return nodeService.save(nodeModel);
    }

    /**
     * 删除
     * @param nodeIds
     * @return
     */
    @PostMapping(value = "/del")
    @ResponseBody
    public BaseResult del(@RequestParam String nodeIds){
        return nodeService.del(nodeIds);
    }

    /**
     * 节点测试
     * @param nodeId
     * @return
     */
    @PostMapping(value = "/test")
    @ResponseBody
    public BaseResult testNode(@RequestParam String nodeId){
        return nodeService.testNode(nodeId);
    }

}
