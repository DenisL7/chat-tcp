package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClientRepositoriy {
    private static ClientRepositoriy clientRepositoriy = new ClientRepositoriy();

    private ClientRepositoriy() {

    }

    public static ClientRepositoriy getInstance() {
        return clientRepositoriy;
    }

    private List<ClientSession> sessions = new ArrayList<>();

    public void add(ClientSession clientSession) {
        sessions.add(clientSession);
    }

    public void sentMessageToAllClients(Map<String, Object> message) throws IOException {
        List<ClientSession> deadClients = new ArrayList<>();
        for (ClientSession session : sessions) {
            if (!session.isClosed()) {
                session.sendMessage(message);
            } else {
                deadClients.add(session);

            }

        }
        sessions.removeAll(deadClients);
    }

    public void removeClient(ClientSession clientSession) {
        sessions.remove(clientSession);
    }
}
