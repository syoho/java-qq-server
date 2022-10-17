package com.java.qqserver.service;


import com.java.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

//该类对应的线程和某个客户端保持通讯
//必须有Socket
//线程
//继承Thread
public class ServerConnectClientThread extends Thread {

    private Socket socket;
    private String userId; //连接到服务器的User对象ID

    //构造器
    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    //run方法
    //保持线程在运行状态
    //可以接收/发送消息
    //while循环

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println("服务器和客户端保持通信，读取数据");
                //读数据
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject(); //为什么是读Message？；后面使用Message

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
