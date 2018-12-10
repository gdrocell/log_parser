package com.ef.common;

import java.sql.Timestamp;

public class LogEntry {
    private Integer logEntryId;
    private Timestamp requestDate;
    private String ip;
    private String httpRequest;
    private Integer status;
    private String userAgent;

    public LogEntry() {
        
    }
    
    public Integer getLogEntryId() {
        return logEntryId;
    }
    
    public void setLogEntryId(Integer logEntryId) {
        this.logEntryId = logEntryId;
    }
    
    public Timestamp getRequestDate() {
        return requestDate;
    }
    
    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getHttpRequest() {
        return httpRequest;
    }
    
    public void setHttpRequest(String httpRequest) {
        this.httpRequest = httpRequest;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
