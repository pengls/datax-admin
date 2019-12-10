package com.dragon.datax.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @ClassName DataOperResultDto
 * @Author pengl
 * @Date 2019-12-09 15:21
 * @Description 操作结果
 * @Version 1.0
 */
@Data
@Builder
public class DataOperResultDto {
    @Tolerate
    public DataOperResultDto(){}

    public DataOperResultDto(String operResult, String startTime, String endTime, String times, String insertCount, String updateCount, String deleteCount, String failCount, String operResultDesc) {
        this.operResult = operResult;
        this.startTime = startTime;
        this.endTime = endTime;
        this.times = times;
        this.insertCount = insertCount;
        this.updateCount = updateCount;
        this.deleteCount = deleteCount;
        this.failCount = failCount;
        this.operResultDesc = operResultDesc;
    }

    private String operResult;
    private String startTime;
    private String endTime;
    private String times;
    private String insertCount;
    private String updateCount;
    private String deleteCount;
    private String failCount;
    private String operResultDesc;

}
