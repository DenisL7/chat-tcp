package com.example.chattcpfx;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NetClient extends Thread {
    private DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
    private TextArea messagesTextArea;

    private String host;
    private int port;
    private String login;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public NetClient(){

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
    public void disconnect() throws IOException {
        socket.close();
    }

    public void run() {
        while (true) {


                try {
                    Map<String, Object> map = (Map<String, Object>) inputStream.readObject();
                    System.out.println("response from server=" + map);
                    Date currentDate=new Date();
                    String result=dateFormat.format(currentDate);
                    String message=result+" "+map.get("login")+": "+map.get("message");
                    messagesTextArea.appendText("\n" + message);
                } catch (Exception ex) {
                    if (!socket.isClosed()) {
                        try {
                            socket.close();
                            break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
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

}
