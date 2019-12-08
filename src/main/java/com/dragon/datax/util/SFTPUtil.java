package com.dragon.datax.util;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName SFTPUtil
 * @Author pengl
 * @Date 2018/11/26 13:31
 * @Description STFP工具类
 * @Version 1.0
 */
public class SFTPUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SFTPUtil.class);
    private String ftpHost;
    private String ftpUserName;
    private String ftpPassword;
    private int ftpPort;
    private Session session;
    private ChannelSftp channel;


    public SFTPUtil(String ftpHost, String ftpUserName,String ftpPassword, int ftpPort) {
        this.ftpHost = ftpHost;
        this.ftpUserName = ftpUserName;
        this.ftpPassword = ftpPassword;
        this.ftpPort = ftpPort;
    }

    /**
     * 连接sftp
     *
     * @return
     * @throws JSchException
     */
    public void connect() throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
        session.setPassword(ftpPassword);
        session.setTimeout(60 * 1000);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        channel = (ChannelSftp)session.openChannel("sftp");
        channel.connect();
    }

    /**
     * @param remotePath 上传到SFTP服务器上文件的路径
     * @param text       要上传的文本
     * @throws JSchException
     */
    public boolean uploadText(String remotePath, String text) throws JSchException, IOException {
        InputStream is = IoUtil.toStream(text, StandardCharsets.UTF_8.name());
        try {
            String[] pathAndFileName = getFilePathAndFileName(remotePath);
            /**
             * 判断远程目录是否存在 并创建目录
             */
            createDir(pathAndFileName[0]);
            channel.put(is, remotePath, ChannelSftp.OVERWRITE);
            return true;
        } catch (Exception e) {
            LOGGER.error("SFTP upload file error : {}", e.getMessage(), e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 根据路径创建文件目录: 包括子目录
     */
    public void createDir(String createpath) throws SftpException {
        if (isDirExist(createpath)) {
            return;
        }
        String pathArry[] = createpath.split("/");
        StringBuffer filePath = new StringBuffer("/");
        for (String path : pathArry) {
            if (path.equals("")) {
                continue;
            }
            filePath.append(path + "/");
            if (isDirExist(filePath.toString())) {
                channel.cd(filePath.toString());
            } else {
                channel.mkdir(filePath.toString());
                channel.cd(filePath.toString());
            }
        }
    }

    /**
     * 判断目录是否存在
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channel.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    public static String[] getFilePathAndFileName(String filePath){
        String[] result = new String[2];
        if(filePath.indexOf(".") == -1){
            result[0] = filePath;
            result[1] = "";
        }else{
            int index = filePath.lastIndexOf("/");
            result[0] = filePath.substring(0, index);
            result[1] = filePath.substring(index + 1, filePath.length());
        }
        return result;
    }

    /**
     * 关闭资源
     */
    public void close() {
        if (channel != null) {
            channel.quit();
            channel = null;
        }
        if (session != null) {
            session.disconnect();
            session = null;
        }
    }

    /**
     * 远程执行命令
     * @param command
     * @return
     */
    public DataOperResult execCmd(String command) throws JSchException, IOException{
        String now = System.currentTimeMillis() + "";
        if(StringUtils.isBlank(command)){
            return new DataOperResult("", "", "2", now, now, "0", "0", "0", "0", "0", "执行命令为空");
        }
        if(session == null){
            return new DataOperResult("", "", "2", now, now, "0", "0", "0", "0", "0", "session对象为空");
        }
        LOGGER.info( "===>>开始执行远程Shell命令:" + command);
        int returnCode  = -1;
        BufferedReader reader = null;
        ChannelExec exeChannel = (ChannelExec)session.openChannel("exec");
        exeChannel.setCommand(command);
        exeChannel.setInputStream(null);
        exeChannel.setErrStream(System.err);
        InputStream in = exeChannel.getInputStream();
        reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        exeChannel.connect();
        String buf ;
        List<String> consoles = new ArrayList<>();
        while ((buf = reader.readLine()) != null) {
            LOGGER.info(buf);
            consoles.add(buf);
        }
        reader.close();
        if (exeChannel.isClosed()) {
            returnCode = exeChannel.getExitStatus();
        }
        LOGGER.info( "远程Shell命令执行完成：" + returnCode );
        exeChannel.disconnect();
        DataOperResult dataOperResult = DataxUtil.parseDataXConsole(consoles);
        dataOperResult.setOperResult(returnCode + "");
        return dataOperResult;
    }
}
