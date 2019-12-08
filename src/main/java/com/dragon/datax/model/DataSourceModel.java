package com.dragon.datax.model;

import java.util.Date;

/**
 * @ClassName DataSourceModel
 * @Author pengl
 * @Date 2018/11/22 10:50
 * @Description 数据源模型
 * @Version 1.0
 */
public class DataSourceModel {
    private String dsId;
    private String dsDesc;
    private String dsUser;
    private String dsPass;
    private String dsType;
    private String dsUrl;
    private int dsStatus;
    private Date createTime;
    private String dsSchema;

    public String getDsId() {
        return dsId;
    }

    public void setDsId(String dsId) {
        this.dsId = dsId;
    }

    public String getDsDesc() {
        return dsDesc;
    }

    public void setDsDesc(String dsDesc) {
        this.dsDesc = dsDesc;
    }

    public String getDsUser() {
        return dsUser;
    }

    public void setDsUser(String dsUser) {
        this.dsUser = dsUser;
    }

    public String getDsPass() {
        return dsPass;
    }

    public void setDsPass(String dsPass) {
        this.dsPass = dsPass;
    }

    public String getDsType() {
        return dsType;
    }

    public void setDsType(String dsType) {
        this.dsType = dsType;
    }

    public String getDsUrl() {
        return dsUrl;
    }

    public void setDsUrl(String dsUrl) {
        this.dsUrl = dsUrl;
    }

    public int getDsStatus() {
        return dsStatus;
    }

    public void setDsStatus(int dsStatus) {
        this.dsStatus = dsStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDsSchema() {
        return dsSchema;
    }

    public void setDsSchema(String dsSchema) {
        this.dsSchema = dsSchema;
    }
}
