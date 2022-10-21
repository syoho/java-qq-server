package com.java.qqserver.service;


import java.util.HashMap;
import java.util.Iterator;

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

    //编写方法-可以返回在线用户列表
    //返回String类型
    //返回格式：100--200--Oreki
    public static String getOnlineUserList() {

        //集合遍历
        //遍历HashMap的key
        //使用迭代器
        Iterator<String> iterator = hm.keySet().iterator();
        String onLineUserList = ""; //返回的对象
        //只要还有下一个
        while (iterator.hasNext()) {
            onLineUserList += iterator.next().toString() + "--";
        }
        return onLineUserList;
    }


}
