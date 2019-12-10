package com.dragon.datax.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dragon.datax.model.Dts;
import com.dragon.datax.service.DtsService;
import com.dragon.datax.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName DtController
 * @Author pengl
 * @Date 2018/11/22 10:32
 * @Description 视图配置
 * @Version 1.0
 */
@Controller
@RequestMapping("")
public class ViewController {
    @Autowired
    private DtsService dtsService;
    @Autowired
    private NodeService nodeService;

    /**
     * 项目首页
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index(ModelAndView model) {
        model.setViewName("task");
        return model;
    }

    /**
     * 数据源配置首页
     *
     * @return
     */
    @RequestMapping("/dts")
    public String dtsIndex() {
        return "datasource/dts.index";
    }

    /**
     * 节点配置首页
     *
     * @return
     */
    @RequestMapping("/node")
    public String nodeIndex() {
        return "node/node.index";
    }

    /**
     * 任务配置首页
     *
     * @return
     */
    @RequestMapping("/task")
    public String taskIndex(Model model) {
        model.addAttribute("dtslist", dtsService.list(new QueryWrapper<Dts>().lambda().orderByDesc(Dts::getCreateTime)));
        model.addAttribute("nodelist", nodeService.list());
        return "task/task.index";
    }
}
