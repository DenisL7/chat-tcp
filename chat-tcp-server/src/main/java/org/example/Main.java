package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("server is started on port 8081");
        ClientRepositoriy clientRepositoriy = ClientRepositoriy.getInstance();
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connection is created");
            ClientSession clientSession = new ClientSession(socket);
            clientRepositoriy.add(clientSession);
            clientSession.start();


        }
    }
}