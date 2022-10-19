package com.java.qqcommon;
import java.io.Serializable;


//Client与Server通行时的消息对象
public class Message implements Serializable {

    //保证序列化的兼容性
    private static final long serialVersionID = 1L;

    private String sender; //消息发送者
    private String getter; //消息接收者
    private String content; //消息内容
    private String sendTime; //发送时间
    private String mesType; //重要，消息类型 -> 去接口中定义消息类型

    //为什么除了mesType外，其他无需构造器？

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
