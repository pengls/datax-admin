package com.dragon.datax.controller;

import com.dragon.datax.service.DsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * @ClassName DtController
 * @Author pengl
 * @Date 2018/11/22 10:32
 * @Description 数据源管理
 * @Version 1.0
 */
@Controller
@RequestMapping("dts")
public class DtsController {
    @Autowired
    private DsService dsService;

    /**
     * 列表
     * @return
     */
    @PostMapping(value="/list")
    public Map<String, List<DataSourceModel>> list(){
        List<DataSourceModel> list =  dsService.findAll();
        Map<String, List<DataSourceModel>> resultMap = new HashMap<>(1);
        resultMap.put("data" , list);
        return resultMap;
    }

    /**
     * 保存
     * @param dataSourceModel
     * @return
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public BaseResult save(@ModelAttribute DataSourceModel dataSourceModel){
        return dsService.save(dataSourceModel);
    }

    /**
     * 删除
     * @param dsIds
     * @return
     */
    @PostMapping(value = "/del")
    @ResponseBody
    public BaseResult del(@RequestParam String dsIds){
        return dsService.del(dsIds);
    }

    /**
     * 数据源测试
     * @param dsId
     * @return
     */
    @PostMapping(value = "/test")
    @ResponseBody
    public BaseResult testDts(String dsId){
        return dsService.testDts(dsId);
    }

    /**
     * 获取数据源下所有的表
     * @param dsId
     * @return
     */
    @PostMapping(value = "/tables")
    @ResponseBody
    public BaseResult getTablesByDsId(@RequestParam String dsId){
        return dsService.getTablesByDsId(dsId);
    }

    /**
     * 获取表下所有的列
     * @param tableName
     * @return
     */
    @PostMapping(value = "/columns")
    @ResponseBody
    public BaseResult getColumnsByTableName(@RequestParam String dsId, @RequestParam String tableName){
        return dsService.getColumnsByTableName(dsId, tableName);
    }

}
