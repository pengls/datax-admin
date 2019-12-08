package com.dragon.datax.model;

import java.util.Date;

/**
 * @ClassName TaskModel
 * @Author pengl
 * @Date 2018/11/23 9:17
 * @Description 任务模型
 * @Version 1.0
 */
public class TaskModel {
    private String taskId;
    private String taskName;
    private String taskDesc;
    private String readerDsName;
    private String writerDsName;
    private String readerDsId;
    private String writerDsId;
    private String taskContent;
    private int status;
    private Date createTime;
    private Date updateTime;
    private String taskSaveName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

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

    public String getReaderDsName() {
        return readerDsName;
    }

    public void setReaderDsName(String readerDsName) {
        this.readerDsName = readerDsName;
    }

    public String getWriterDsName() {
        return writerDsName;
    }

    public void setWriterDsName(String writerDsName) {
        this.writerDsName = writerDsName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTaskSaveName() {
        return taskSaveName;
    }

    public void setTaskSaveName(String taskSaveName) {
        this.taskSaveName = taskSaveName;
    }
}
