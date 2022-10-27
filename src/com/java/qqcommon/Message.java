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

    //与文件传输相关的成员
    //为文件内容增加的代码 - content内
    //进行扩展
    //和文件相关的成员
    //字节数组方式 - 进行文件传输
    private byte[] fileBytes;
    private int fileLength = 0; //文件长度
    private String dest; //文件传输的目的地地址
    private String src; //源文件路径

    //getter - setter方法
    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

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
