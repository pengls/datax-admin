package com.dragon.datax.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * @ClassName Dts
 * @Author pengl
 * @Date 2018/11/22 10:50
 * @Description 数据源模型
 * @Version 1.0
 */
@Data
@Builder
@TableName("dts")
public class Dts {
    @Tolerate
    public Dts(){}
    @TableId
    private String id;
    private String name;
    private String user;
    private String pass;
    private String dbType;
    private String jdbcUrl;
    private int status;
    private Date createTime;
    private String dsSchema;
    private int delStatus;

}
