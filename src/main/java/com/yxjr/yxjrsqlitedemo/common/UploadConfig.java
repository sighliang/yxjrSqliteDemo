package com.yxjr.yxjrsqlitedemo.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UploadConfig {
    //客户端端口号
    @Value(value = "${server.port}")
    private  int port;
    //服务器存放更新包的文件夹路径
    @Value(value ="${uploadpacketPath}" )
    private String uploadpacketPath;
    //日志存放路径
    @Value(value = "${logPath}")
    private String logPath;

    @Value(value = "${dbPath}")
    private String dbPath;

    @Value(value = "${serverIp}")
    private String ip;

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUploadpacketPath() {
        return uploadpacketPath;
    }

    public void setUploadpacketPath(String uploadpacketPath) {
        this.uploadpacketPath = uploadpacketPath;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
