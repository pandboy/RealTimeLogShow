package org.xfl.onlinelog.bean;

/**
 * Created by bl04559 on 2016/8/23.
 */
public class PublishInfo {
    /**
     * 生产服务器
     */
    private String ip;

    /**
     * 密码
     */
    private String password;
    /**
     * 发布类型 1，生产发布，0生产回滚
     */
    private Integer publishType;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPublishType() {
        return publishType;
    }

    public void setPublishType(Integer publishType) {
        this.publishType = publishType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(ip);
        sb.append(" ")
                .append(publishType).append(" ")
                .append(password);
        return  sb.toString();
    }
}
