package com.java.qqserver.service;


import java.util.HashMap;

//管理服务器端-与客户端通信的线程
public class ManageServerConnectClientThread {

    //创建一个HashMap
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    //一定需要一个添加线程的方法
    public static void addServerConnectClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {
        //放入用put() - HashMap的方法
        hm.put(userId, serverConnectClientThread);
    }

    //有添加，则有获取
    //根据userId返回serverConnectClientThread
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }

}
