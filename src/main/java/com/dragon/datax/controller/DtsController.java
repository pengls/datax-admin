package com.dragon.datax.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.model.Dts;
import com.dragon.datax.service.DtsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @ClassName DtController
 * @Author pengl
 * @Date 2018/11/22 10:32
 * @Description 数据源管理
 * @Version 1.0
 */
@RestController
@RequestMapping("dts")
public class DtsController {
    @Autowired
    private DtsService dtsService;

    /**
     * 列表
     * @return
     */
    @PostMapping(value="/list")
    public Map<String, List<Dts>> list(){
        List<Dts> list =  dtsService.list(new QueryWrapper<Dts>().lambda().orderByDesc(Dts::getCreateTime));
        Map<String, List<Dts>> resultMap = new HashMap<>(1);
        resultMap.put("data" , list);
        return resultMap;
    }

    /**
     * 保存
     * @param dts
     * @return
     */
    @PostMapping(value = "/save")
    public Result save(@ModelAttribute Dts dts){
        DbType dbType = JdbcUtils.getDbType(dts.getJdbcUrl());
        if(DbType.OTHER.equals(dbType)){
            return new Result(false, -1, "JDBC URL无法识别");
        }
        dts.setDbType(dbType.getDb());

        if(StringUtils.isBlank(dts.getId())){
            dts.setId(IdUtil.fastSimpleUUID());
            dts.setCreateTime(new Date());
        }

        return new Result(dtsService.saveOrUpdate(dts));
    }

    /**
     * 删除
     * @param dsIds
     * @return
     */
    @PostMapping(value = "/del")
    public Result del(@RequestParam String dsIds){
        return new Result(dtsService.removeByIds(Arrays.asList(dsIds.split(","))));
    }

    /**
     * 数据源测试
     * @param dsId
     * @return
     */
    @PostMapping(value = "/test")
    @ResponseBody
    public Result testDts(String dsId){
        return dtsService.testDts(dsId);
    }

    /**
     * 获取数据源下所有的表
     * @param dsId
     * @return
     */
    @PostMapping(value = "/tables")
    public Result getTablesByDsId(@RequestParam String dsId){
        return dtsService.getTablesByDsId(dsId);
    }

    /**
     * 获取表下所有的列
     * @param tableName
     * @return
     */
    @PostMapping(value = "/columns")
    @ResponseBody
    public Result getColumnsByTableName(@RequestParam String dsId, @RequestParam String tableName){
        return dtsService.getColumnsByTableName(dsId, tableName);
    }

}
