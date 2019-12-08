package com.dragon.datax.model;

/**
 * @ClassName DataxRDBMSConfigModel
 * @Author pengl
 * @Date 2018/11/24 20:05
 * @Description Datax 关系型数据库任务配置模型
 * @Version 1.0
 */
public class DataxRDBMSConfigModel {

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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String[] getPushNodes() {
        return pushNodes;
    }

    public void setPushNodes(String[] pushNodes) {
        this.pushNodes = pushNodes;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getReaderDsId() {
        return readerDsId;
    }

    public void setReaderDsId(String readerDsId) {
        this.readerDsId = readerDsId;
    }

    public String getWriterDsId() {
        return writerDsId;
    }

    public void setWriterDsId(String writerDsId) {
        this.writerDsId = writerDsId;
    }

    public String[] getReaderColumns() {
        return readerColumns;
    }

    public void setReaderColumns(String[] readerColumns) {
        this.readerColumns = readerColumns;
    }

    public String[] getWriterColumns() {
        return writerColumns;
    }

    public void setWriterColumns(String[] writerColumns) {
        this.writerColumns = writerColumns;
    }

    public String getReaderTableName() {
        return readerTableName;
    }

    public void setReaderTableName(String readerTableName) {
        this.readerTableName = readerTableName;
    }

    public String getWriterTableName() {
        return writerTableName;
    }

    public void setWriterTableName(String writerTableName) {
        this.writerTableName = writerTableName;
    }

    public String getSplitPk() {
        return splitPk;
    }

    public void setSplitPk(String splitPk) {
        this.splitPk = splitPk;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getWriteMode() {
        return writeMode;
    }

    public void setWriteMode(String writeMode) {
        this.writeMode = writeMode;
    }

    public String getPreSql() {
        return preSql;
    }

    public void setPreSql(String preSql) {
        this.preSql = preSql;
    }

    public String getPostSql() {
        return postSql;
    }

    public void setPostSql(String postSql) {
        this.postSql = postSql;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaskSaveName() {
        return taskSaveName;
    }

    public void setTaskSaveName(String taskSaveName) {
        this.taskSaveName = taskSaveName;
    }
}
