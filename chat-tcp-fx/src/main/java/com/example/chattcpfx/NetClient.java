package com.example.chattcpfx;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class NetClient extends Thread {
    private TextArea messagesTextArea;

    private static NetClient instance=new NetClient();
    private String host;
    private int port;
    private String login;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private NetClient(){

    }
    public TextArea getMessagesTextArea() {
        return messagesTextArea;
    }

    public void setMessagesTextArea(TextArea messagesTextArea) {
        this.messagesTextArea = messagesTextArea;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void connect() throws IOException {
        socket=new Socket(InetAddress.getByName(host),port);
        outputStream=new ObjectOutputStream(socket.getOutputStream());
        inputStream=new ObjectInputStream(socket.getInputStream());
        start();
    }

    public void run() {
        while (true) {
            try {
                Map<String, Object> map = (Map<String, Object>) inputStream.readObject();
                System.out.println("response from server=" + map);
                messagesTextArea.appendText("\n" + map);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void sentMessage(String message)   {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("message", message);
            map.put("login", login);
            outputStream.writeObject(map);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static NetClient getInstance(){
        return instance;
    }
}
