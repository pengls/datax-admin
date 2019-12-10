package com.dragon.datax.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dragon.boot.common.model.Result;
import com.dragon.datax.mapper.DtsMapper;
import com.dragon.datax.model.Dts;
import com.dragon.datax.service.DtsService;
import com.dragon.datax.util.db.DBHandle;
import com.dragon.datax.util.db.JdbcConnect;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * @ClassName DtsServiceImpl
 * @Author pengl
 * @Date 2019-12-09 13:09
 * @Description 数据源管理业务层
 * @Version 1.0
 */
@Service
public class DtsServiceImpl extends ServiceImpl<DtsMapper, Dts> implements DtsService {

    @Override
    public Result testDts(String dsId) {
        Dts dts = this.getById(dsId);
        if (dts == null) {
            return new Result(false, -1, "数据源不存在！");
        }
        Connection conn = null;
        try {
            JdbcConnect jdbcConnect = new JdbcConnect(dts);
            conn = jdbcConnect.getConnection();
        } catch (Exception e) {
            return new Result(false, -1, e.getMessage());
        } finally {
            JdbcConnect.closeConnection(conn);
        }
        return new Result();
    }

    @Override
    public Result getTablesByDsId(String dsId) {
        Dts dts = this.getById(dsId);
        if (dts == null) {
            return new Result(false, -1, "数据源不存在");
        }
        try {
            JdbcConnect jdbcConnect = new JdbcConnect(dts);
            Connection conn = jdbcConnect.getConnection();
            List<String> list = DBHandle.getTables(conn, dts.getDsSchema());
            return new Result<>(list);
        } catch (Exception e) {
            return new Result(false, -1, e.getMessage());
        }
    }

    @Override
    public Result getColumnsByTableName(String dsId, String tableName) {
        Dts dts = this.getById(dsId);
        if (dts == null) {
            return new Result(false, -1, "数据源不存在");
        }
        try {
            JdbcConnect jdbcConnect = new JdbcConnect(dts);
            Connection conn = jdbcConnect.getConnection();
            List<String> list = DBHandle.getColums(conn, dts.getDsSchema(), tableName);
            return new Result(list);
        } catch (Exception e) {
            return new Result(false, -1, e.getMessage());
        }
    }
}
