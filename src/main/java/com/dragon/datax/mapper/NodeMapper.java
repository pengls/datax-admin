package com.dragon.datax.mapper;

import com.dragon.datax.model.NodeModel;

import java.util.List;

/**
 * @ClassName DsMapper
 * @Author pengl
 * @Date 2018/11/22 10:52
 * @Description 数据源管理
 * @Version 1.0
 */
public interface NodeMapper {
    /**
     * 查询数据源列表
     * @return
     */
    List<NodeModel> findAll();

    /**
     * 插入数据源
     * @return
     */
    int insertNode(NodeModel nodeModel);

    /**
     * 更新数据源
     * @param nodeModel
     * @return
     */
    int updateNodeById(NodeModel nodeModel);

    /**
     * 删除数据源
     * @param id
     * @return
     */
    int delNodeById(String id);


    /**
     * 通过ID查询数据源
     * @param nodeId
     * @return
     */
    NodeModel findById(String nodeId);
}
