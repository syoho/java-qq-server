package com.java.qqserver.service;

//服务端
//监听9999端口
//等待客户端连接，保持通信

import com.java.common.Message;
import com.java.common.MessageType;
import com.java.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class QQServer {

    //核心的ServerSocket
    private ServerSocket serverSocket = null;

    //构造器
    public QQServer() {

        //端口可写在配置文件中
        try {
            System.out.println("服务器在端口9999监听");
            serverSocket = new ServerSocket(9999);

            //循环监听
            //while
            //和某个客户端连接后，还需要继续监听
            while (true) {
                //如果没有客户端连接，就会阻塞
                Socket socket = serverSocket.accept(); //此处得到服务器端的socket

                //得到socket关联的对象输入流
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                //得到socket关联的对象输出流
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                //读
                User user = (User) objectInputStream.readObject(); //客户端发来的是User对象，强制转换；读取
                //理论上应该去数据库验证发来的User
                //此处先强制设置用户ID和密码
                //创建一个Message对象，准备回复客户端；失败与否，都要创建Message
                Message message = new Message();

                if (user.getUserId().equals("100") && user.getPasswd().equals("12345")) {
                    //登录成功
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //将Message对象回复
                    objectOutputStream.writeObject(message);
                    //创建线程-建类-持有Socket
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, user.getUserId());
                    //启动线程
                    serverConnectClientThread.start();
                    //将该线程放入一个集合中，用于管理
                    ManageServerConnectClientThread.addServerConnectClientThread(user.getUserId(), serverConnectClientThread);



                } else {
                    //登录失败
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    objectOutputStream.writeObject(message);
                    //关闭socket
                    socket.close();

                }

            }



        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            //如果服务器退出了while，则说明服务器不再监听，因此关闭ServerSocket
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
