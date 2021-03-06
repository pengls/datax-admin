package com.dragon.datax.util;

import com.alibaba.fastjson.JSONObject;
import com.dragon.datax.dto.DataxRDBMSConfigDto;
import com.dragon.datax.model.Dts;
import com.dragon.datax.service.DtsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @ClassName DataxTemplateUtil
 * @Author pengl
 * @Date 2018/11/24 20:03
 * @Description 获取任务模版
 * @Version 1.0
 */
@Component
public class DataxTemplateUtil {
    @Autowired
    private DtsService dtsService;

    /**
     * 关系型数据库任务模版
     *
     * @return
     */
    public String getRDBMSTemplate(DataxRDBMSConfigDto configModel) {
        /**
         * 参数校验
         */
        String readerDsId = configModel.getReaderDsId();
        String writerDsId = configModel.getWriterDsId();
        String reader = configModel.getReader();
        String writer = configModel.getWriter();
        if (StringUtils.isAnyBlank(readerDsId, writerDsId, reader, writer)) {
            return "";
        }

        /**
         * 获取读写数据源
         */
        Dts rdS = dtsService.getById(readerDsId);
        Dts wrS = dtsService.getById(writerDsId);
        if (rdS == null || wrS == null) {
            return "";
        }

        /**
         * 拼接querySql
         */
        String querySql = configModel.getQuerySql();
        String where = configModel.getWhere();
        if (StringUtils.isBlank(querySql)) {
            String[] readerColumns = configModel.getReaderColumns();
            String feilds = StringUtils.join(readerColumns, ",");
            String readerTableName = configModel.getReaderTableName();
            if (StringUtils.isNotBlank(rdS.getDsSchema())) {
                readerTableName = rdS.getDsSchema() + "." + readerTableName;
            }
            if (StringUtils.isBlank(where)) {
                querySql = MessageFormat.format("SELECT {0} FROM {1}", feilds, readerTableName);
            } else {
                querySql = MessageFormat.format("SELECT {0} FROM {1} WHERE {2}", feilds, readerTableName, configModel.getWhere());
            }
        }

        /**
         * 设置默认参数
         */
        String channel = StringUtils.isBlank(configModel.getChannel()) ? "1" : configModel.getChannel();


        String template = "{\"job\":{\"setting\":{\"speed\":{\"channel\":\"%s\"}},\"content\":[{\"reader\":{\"name\":\"%s\",\"parameter\":{\"username\":\"%s\",\"password\":\"%s\",\"connection\":[{\"querySql\":[\"%s\"],\"jdbcUrl\":[\"%s\"]}]}},\"writer\":{\"name\":\"%s\",\"parameter\":{\"writeMode\":\"%s\",\"username\":\"%s\",\"password\":\"%s\",\"column\":[\"\"],\"session\":[\"set session sql_mode='ANSI'\"],\"preSql\":[\"%s\"],\"postSql\":[\"%s\"],\"connection\":[{\"jdbcUrl\":\"%s\",\"table\":[\"%s\"]}]}}}]}}";
        template = String.format(template, channel, configModel.getReader(), rdS.getUser(), rdS.getPass(),
                querySql, rdS.getJdbcUrl(), configModel.getWriter(), configModel.getWriteMode(),
                wrS.getUser(), wrS.getPass(),
                configModel.getPreSql(),
                configModel.getPostSql(),
                wrS.getJdbcUrl(),
                configModel.getWriterTableName());

        JSONObject tempObj = JSONObject.parseObject(template);
        JSONObject paraObj = tempObj.getJSONObject("job").getJSONArray("content").getJSONObject(0).getJSONObject("writer").getJSONObject("parameter");
        paraObj.put("column", configModel.getWriterColumns());
        return tempObj.toJSONString();
    }

    public static void main(String[] args) {

    }
}
