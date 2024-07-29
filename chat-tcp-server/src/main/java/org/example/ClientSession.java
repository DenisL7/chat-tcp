package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class ClientSession extends Thread {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private ClientRepositoriy clientRepositoriy;

    public ClientSession(Socket socket) throws IOException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        clientRepositoriy = ClientRepositoriy.getInstance();
    }

    public void run() {
        while (true) {
            try {
                Map<String, Object> clientMessage = (Map<String, Object>) inputStream.readObject();
                clientRepositoriy.sentMessageToAllClients(clientMessage);

            } catch (Exception e) {
                //throw new RuntimeException(e);
                try {
                    socket.close();
                    System.out.println("client was closed");
                    break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public void sendMessage(Map<String, Object> clientMessage) throws IOException {
        outputStream.writeObject(clientMessage);
    }

    public boolean isClosed() {
        return socket.isClosed();
    }
}
