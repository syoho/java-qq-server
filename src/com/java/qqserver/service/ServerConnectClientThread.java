package com.java.qqserver.service;


import com.java.qqcommon.Message;
import com.java.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

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

    public Socket getSocket() {
        return socket;
    }

    //run方法
    //保持线程在运行状态
    //可以接收/发送消息
    //while循环

    @Override
    public void run() {

        while (true) {
            try {

                //从数据通道得到ObjectInputStream - 转换为Message对象               System.out.println("服务器和客户端用户ID："+ userId +"保持通信，读取数据中……" );
                //读数据
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject(); //为什么是读Message？；后面使用Message

                //socket.getInputStream() - ObjectInputStream - .readObject() -Message
                //根据Message类型，做相应的业务处理
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {

                    //客户端请求在线列表
                    //便于debug
                    //在线用户列表形式：
                    System.out.println(message.getSender() + "要求在线用户列表");

                    //管理线程的集合知道有多少线程
                    //去ManageServerConnectClientThread.java编写方法-可以返回在线用户列表
                    String onlineUserList = ManageServerConnectClientThread.getOnlineUserList();

                    //需要返回
                    //返回Message
                    Message message_return = new Message();
                    message_return.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message_return.setContent(onlineUserList);
                    message_return.setGetter(message.getSender());

                    //返回客户端
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message_return);

                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {

                    //转发
                    //根据Message获取getterId - 得到对应线程
                    ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getGetter());//返回一个线程？
                    //得到对应socket的对象输出流
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    objectOutputStream.writeObject(message); //转发，提示如果客户不在线，可以保存到数据库，实现离线留言


                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {

                    //需要遍历ManageServerConnectClientThread集合
                    //把所有线程的Socket得到
                    //把Message转发
                    //要遍历 - 得到HashMap - HashMap为私有 - 去ManageServerConnectClientThread写方法 - get
                    //重看【集合】
                    HashMap<String, ServerConnectClientThread> hm = ManageServerConnectClientThread.getHm(); //这样就有所有的线程啦？难以置信

                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {

                        //取出在线用户的id
                        String onLineUserId = iterator.next().toString(); //取出集合中的key

                        //如果服务器线程对应的userId不是客户端发送者的id，则群发消息
                        //有点意思，韩顺平厉害！
                        if (!onLineUserId.equals(message.getSender())) {

                            //进行转发
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                            objectOutputStream.writeObject(message);

                        }
                    }


                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {

                    //根据getterId，获得相应的线程
                    //将message对象转发
                    ServerConnectClientThread serverConnectClientThread = ManageServerConnectClientThread.getServerConnectClientThread(message.getGetter());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());

                    //转发
                    objectOutputStream.writeObject(message);

                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {

                    //客户端退出线程
                    System.out.println(message.getSender() + "退出");

                    //将客户端对应的线程从集合中移除
                    ManageServerConnectClientThread.removeServerConnectClientThread(message.getSender());

                    //关闭连接
                    socket.close();

                    //退出
                    break;

                } else {
                    System.out.println("其他类型暂不做处理……");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
