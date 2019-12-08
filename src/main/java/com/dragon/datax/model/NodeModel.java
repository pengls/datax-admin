package com.dragon.datax.model;

/**
 * @ClassName NodeModel
 * @Author pengl
 * @Date 2018/11/26 10:48
 * @Description TODO
 * @Version 1.0
 */
public class NodeModel {
    private String nodeId;
    private String nodeIp;
    private int nodePort;
    private int nodeType;
    private int nodeStatus;
    private String nodeLoginUser;
    private String nodeLoginPass;
    private String nodeDefaultPath;
    private String nodeDataxPath;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public int getNodePort() {
        return nodePort;
    }

    public void setNodePort(int nodePort) {
        this.nodePort = nodePort;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public int getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(int nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getNodeLoginUser() {
        return nodeLoginUser;
    }

    public void setNodeLoginUser(String nodeLoginUser) {
        this.nodeLoginUser = nodeLoginUser;
    }

    public String getNodeLoginPass() {
        return nodeLoginPass;
    }

    public void setNodeLoginPass(String nodeLoginPass) {
        this.nodeLoginPass = nodeLoginPass;
    }

    public String getNodeDefaultPath() {
        return nodeDefaultPath;
    }

    public void setNodeDefaultPath(String nodeDefaultPath) {
        this.nodeDefaultPath = nodeDefaultPath;
    }

    public String getNodeDataxPath() {
        return nodeDataxPath;
    }

    public void setNodeDataxPath(String nodeDataxPath) {
        this.nodeDataxPath = nodeDataxPath;
    }
}
