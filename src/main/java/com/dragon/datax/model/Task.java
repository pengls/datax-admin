package com.dragon.datax.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Date;

/**
 * @ClassName Task
 * @Author pengl
 * @Date 2018/11/23 9:17
 * @Description 任务模型
 * @Version 1.0
 */
@Data
@Builder
@TableName("task")
public class Task {
    @Tolerate
    public Task(){}
    @TableId
    private String id;
    private String name;
    private String remark;
    @TableField(exist = false)
    private String readerDsName;
    @TableField(exist = false)
    private String writerDsName;
    private String readerDsId;
    private String writerDsId;
    private String content;
    private int status;
    private Date createTime;
    private Date updateTime;
    private String taskSaveName;
    private int delStatus;
}
