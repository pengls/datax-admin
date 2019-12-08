package com.dragon.datax.mapper;


/**
 * @ClassName DsMapper
 * @Author pengl
 * @Date 2018/11/22 10:52
 * @Description 数据源管理
 * @Version 1.0
 */
public interface DsMapper {
    /**
     * 查询数据源列表
     * @return
     */
    List<DataSourceModel> findAll();

    /**
     * 插入数据源
     * @return
     */
    int insertDs(DataSourceModel dataSourceModel);

    /**
     * 更新数据源
     * @param dataSourceModel
     * @return
     */
    int updateDsById(DataSourceModel dataSourceModel);

    /**
     * 删除数据源
     * @param id
     * @return
     */
    int delDsById(String id);

    /**
     * 通过ID查询数据源
     * @param dsId
     * @return
     */
    DataSourceModel findById(String dsId);
}
