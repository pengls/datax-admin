package com.dragon.datax.util;

import com.dragon.datax.dto.DataOperResultDto;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName DataxUtil
 * @Author pengl
 * @Date 2018/11/27 19:39
 * @Description 工具类
 * @Version 1.0
 */
public class DataxUtil {
    /**
     * @MethodName: 通过控制台打印信息解析出DataX的执行结果
     * @Author: pengl
     * @Date: 2018/10/17 9:51
     * @Description: TODO
     * @Version: 1.0
     * @Param:
     * @Return:
     **/
    public static DataOperResultDto parseDataXConsole(List<String> consoles) {
        DataOperResultDto dataOperResult = new DataOperResultDto();
        boolean flag = false;
        String except = "";
        for (String cons : consoles) {
            if (cons.indexOf("任务启动时刻") != -1) {
                int i = cons.indexOf(":");
                String startTime = cons.substring(i + 2);
                dataOperResult.setStartTime(date2TimeStamp(startTime, "yyyy-MM-dd HH:mm:ss"));
                dataOperResult.setOperResult("1" + "");
                flag = true;
            }
            if (cons.indexOf("任务结束时刻") != -1) {
                int i = cons.indexOf(":");
                dataOperResult.setEndTime(date2TimeStamp(cons.substring(i + 2), "yyyy-MM-dd HH:mm:ss"));
            }
            if (cons.indexOf("任务总计耗时") != -1) {
                int i = cons.indexOf(":");
                cons = cons.substring(i + 2);
                dataOperResult.setTimes(cons.trim());
            }
            if (cons.indexOf("读出记录总数") != -1) {
                int i = cons.indexOf(":");
                cons = cons.substring(i + 2);
                cons = cons.trim();
                dataOperResult.setUpdateCount(cons);
                dataOperResult.setInsertCount("0");
                dataOperResult.setDeleteCount("0");
            }
            if (cons.indexOf("读写失败总数") != -1) {
                int i = cons.indexOf(":");
                cons = cons.substring(i + 2);
                dataOperResult.setFailCount(cons.trim());
            }
            if (cons.indexOf("StandAloneJobContainerCommunicator") != -1) {
                String[] lines = cons.split(" - ");
                dataOperResult.setOperResultDesc(lines[1]);
            }
            if (cons.indexOf("com.alibaba.datax.common.exception.DataXException") != -1) {
                except = cons;
                break;
            }
        }

        if (!flag) {
            String now = System.currentTimeMillis() + "";
            dataOperResult = new DataOperResultDto("-1", now, now, "0", "0", "0", "0", "0", except);
        }
        return dataOperResult;
    }

    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
