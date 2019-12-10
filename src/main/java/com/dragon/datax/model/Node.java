package com.dragon.datax.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @ClassName Node
 * @Author pengl
 * @Date 2018/11/26 10:48
 * @Description 节点模型
 * @Version 1.0
 */
@Data
@Builder
@TableName("node")
public class Node {
    @Tolerate
    public Node(){}
    @TableId
    private String id;
    private String ip;
    private int port;
    private int type;
    private int status;
    private String user;
    private String pass;
    private String defaultPath;
    private String dataxPath;
    private int delStatus;
}
