package com.java.qqserver.service;


import com.java.qqcommon.Message;
import com.java.qqcommon.MessageType;
import com.java.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class SendNewsToAllService implements Runnable {

    private Scanner scanner = new Scanner(System.in); //?

    @Override
    public void run() {

        //为了可以多次推送新闻，使用while
        while (true) {
            System.out.println("请输入服务器要推送的新闻/消息【输入exit表示退出推送服务】：");
            String news = Utility.readString(100);
            if ("exit".equals(news)) {
                break;
            }
            //构建一个消息
            Message message = new Message();
            message.setSender("服务器");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());

            System.out.println("服务器推送消息给所有人：" + news);

            //遍历当前所有的通信线程
            //得到socket
            //发送message

            HashMap<String, ServerConnectClientThread> hm = ManageServerConnectClientThread.getHm();

            //迭代器遍历
            Iterator<String> iterator = hm.keySet().iterator();

            while (iterator.hasNext()) {
                String onLineUserId = iterator.next().toString();
                ServerConnectClientThread serverConnectClientThread = hm.get(onLineUserId);
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
