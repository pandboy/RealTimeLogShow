package org.xfl.onlinelog.bean;


/**
 * Created by bl04559 on 2016/8/17.
 */
public class Message {
    private Integer msgType;
    private String content;

    private PublishInfo publishInfo;

    public Message() {

    }
    public Message(Integer msgType) {
        this.msgType = msgType;
    }


    public Message(Integer msgType, String content) {
        this.msgType = msgType;
        this.content = content;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PublishInfo getPublishInfo() {
        return publishInfo;
    }

    public void setPublishInfo(PublishInfo publishInfo) {
        this.publishInfo = publishInfo;
    }
}
