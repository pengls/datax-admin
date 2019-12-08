package com.dragon.datax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName DtController
 * @Author pengl
 * @Date 2018/11/22 10:32
 * @Description 视图控制
 * @Version 1.0
 */
@Controller
public class ViewController {
    /**
     * 数据源配置首页
     * @return
     */
    @RequestMapping("/dts")
    public String index(){
        return "datasource/dts.index";
    }
}
