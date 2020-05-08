package com.ljz.diagnostic_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ljz.diagnostic_system.common.Utils.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class Log implements Serializable {
    private String logId;
    private String type;
    private String title;
    private String remoteAddr; //请求地址
    private String requestUri;
    private String method; //请求方式
    private String params; //提交参数
    private String exception; //异常
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operateDate; //开始时间
    private String timeout; //结束时间
    private String userId;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 当请求中携带多个参数
     * @param paramsMap 请求参数
     */
    public void setMapToParams(Map<String,String[]> paramsMap){
        if (paramsMap==null) return;
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String,String[]> param:paramsMap.entrySet()){
            params.append("".equals(params.toString()) ? "" : "&").append(param.getKey()).append("=");
            String paramValue = (param.getValue()!=null&&param.getValue().length>0?param.getValue()[0]:"");
            //params.append(StringUtils.substring(StringUtils.endsWithIgnoreCase(param.getKey(),"password")?"":paramValue,100));
            params.append(StringUtils.substring("password".equals(param.getKey())?"":paramValue,0,100));
            this.params = params.toString();
        }
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
