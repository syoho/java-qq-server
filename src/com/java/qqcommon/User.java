package com.java.qqcommon;

import java.io.Serializable;



//表示一个客户信息
//implements Serializable：序列化 ； 一个对象需要通过Object流来读写，对象对应的类需要【序列化】


public class User implements Serializable {

    //保证序列化的兼容性
    private static final long serialVersionID = 1L;

    private String userId; //用户Id
    private String passwd; //用户密码

    //无参构造器
    //为了UserClientService.java中使用
    public User() {}

    //构造器，setter，getter方法
    //按alt+insert（ MAC 里使用组合键：Command+n）

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
