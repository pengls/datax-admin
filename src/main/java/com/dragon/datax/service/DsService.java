package com.dragon.datax.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * @ClassName DsService
 * @Author pengl
 * @Date 2018/11/22 10:55
 * @Description 数据源管理
 * @Version 1.0
 */
@Service
public class DsService {
    @Autowired
    private DsMapper dsMapper;

    /**
     * 查询所有数据源
     * @return
     */
    public List<DataSourceModel> findAll(){
        return dsMapper.findAll();
    }

    /**
     * 通过ID查询数据源
     * @return
     */
    public DataSourceModel findById(String dsId){
        return dsMapper.findById(dsId);
    }

    /**
     * 保存数据源
     * @param dataSourceModel
     * @return
     */
    public BaseResult save(DataSourceModel dataSourceModel) {
        String uid = dataSourceModel.getDsId();
        String dsType = JdbcUtils.getDbType(dataSourceModel.getDsUrl(), null);
        if(StringUtils.isBlank(dsType)){
            return new BaseResult(false, "-1", "JDBC URL无法识别");
        }
        int i = 0;
        if(StringUtils.isBlank(uid)){
            dataSourceModel.setCreateTime(new Date());
            dataSourceModel.setDsType(dsType);
            i = dsMapper.insertDs(dataSourceModel);
        }else{
            dataSourceModel.setDsType(dsType);
            i = dsMapper.updateDsById(dataSourceModel);
        }

        if(i > 0){
            return new BaseResult(true, "0" , dataSourceModel.getDsId());
        }
        return new BaseResult(false, "-1", "操作失败");
    }

    /**
     * 删除数据源
     * @param dsIds
     * @return
     */
    public BaseResult del(String dsIds) {
        String[] usrid = dsIds.split(",");
        int i = 0;
        for(String id : usrid){
            i = i + dsMapper.delDsById(id);
        }
        return new BaseResult(true, "0", i + "");
    }

    /**
     * 测试数据源
     * @param dsId
     * @return
     */
    public BaseResult testDts(String dsId){
        DataSourceModel dataSourceModel = dsMapper.findById(dsId);
        if(dataSourceModel == null){
            return new BaseResult(false, "-1" , "数据源不存在！");
        }
        Connection conn = null;
        try {
            JDBCConnect jdbcConnect = new JDBCConnect(dataSourceModel);
            conn = jdbcConnect.getConnection();
        } catch (Exception e) {
            return new BaseResult(false , "-1", e.getMessage());
        } finally {
            JDBCConnect.closeConnection(conn);
        }
        return new BaseResult();
    }

    /**
     * 通过数据源ID获取所有的表
     * @param dsId
     * @return
     */
    public BaseResult getTablesByDsId(String dsId) {
        DataSourceModel dsm = dsMapper.findById(dsId);
        if(dsm == null){
            return new BaseResult(false, "-1", "数据源不存在");
        }
        try {
            JDBCConnect jdbcConnect = new JDBCConnect(dsm);
            Connection conn = jdbcConnect.getConnection();
            List<String> list = DBHandle.getTables(conn, dsm.getDsSchema());
            ObjectResult ort = new ObjectResult(true, "0");
            ort.setData(list);
            return ort;
        } catch (Exception e) {
            return new BaseResult(false , "-1", e.getMessage());
        }
    }

    /**
     * 获取所有的列
     * @param dsId
     * @param tableName
     * @return
     */
    public BaseResult getColumnsByTableName(String dsId, String tableName) {
        DataSourceModel dsm = dsMapper.findById(dsId);
        if(dsm == null){
            return new BaseResult(false, "-1", "数据源不存在");
        }
        try {
            JDBCConnect jdbcConnect = new JDBCConnect(dsm);
            Connection conn = jdbcConnect.getConnection();
            List<String> list = DBHandle.getColums(conn, dsm.getDsSchema(), tableName);
            ObjectResult ort = new ObjectResult(true, "0");
            ort.setData(list);
            return ort;
        } catch (Exception e) {
            return new BaseResult(false , "-1", e.getMessage());
        }
    }
}
