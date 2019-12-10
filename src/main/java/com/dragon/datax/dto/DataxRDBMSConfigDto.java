package com.dragon.datax.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @ClassName DataxRDBMSConfigDto
 * @Author pengl
 * @Date 2018/11/24 20:05
 * @Description Datax 关系型数据库任务配置模型
 * @Version 1.0
 */
@Data
@Builder
public class DataxRDBMSConfigDto {
    @Tolerate
    public DataxRDBMSConfigDto(){}

    private String taskName;
    private String taskDesc;
    private String taskId;
    private String channel;
    private String reader;
    private String writer;
    private String readerDsId;
    private String writerDsId;
    private String[] readerColumns;
    private String[] writerColumns;
    private String readerTableName;
    private String writerTableName;
    private String splitPk;
    private String querySql;
    private String where;
    private String writeMode;
    private String preSql;
    private String postSql;
    private String[] pushNodes;
    private String taskContent;
    private int status;
    private String taskSaveName;

}
