package com.dragon.datax.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.model.Dts;

/**
 * @ClassName DtsService
 * @Author pengl
 * @Date 2018/11/22 10:55
 * @Description 数据源管理
 * @Version 1.0
 */
public interface DtsService extends IService<Dts> {
    /**
     * @MethodName: testDts
     * @Author: pengl
     * @Date: 2019-12-09 14:03
     * @Description: 数据源测试
     * @Version: 1.0
     * @Param:
     * @Return:
     **/
    Result testDts(String dsId);

    /**
     * @MethodName: getTablesByDsId
     * @Author: pengl
     * @Date: 2019-12-09 14:15
     * @Description: 获取数据源下所有的表名
     * @Version: 1.0
     * @Param:
     * @Return:
     **/
    Result getTablesByDsId(String dsId);

    /**
     * @MethodName: getColumnsByTableName
     * @Author: pengl
     * @Date: 2019-12-09 14:19
     * @Description: 获取表下所有的列
     * @Version: 1.0
     * @Param:
     * @Return:
     **/
    Result getColumnsByTableName(String dsId, String tableName);
}
