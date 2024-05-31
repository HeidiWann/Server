package model;

import view.ClientConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectedClients {
    private ArrayList<ClientConnection> connectedClients;
    private final ReentrantLock lock;
    private Iterator<ClientConnection> iterator;

    public ConnectedClients() {
        this.connectedClients = new ArrayList<>();
        lock = new ReentrantLock(true);
    }

    public void addClient(ClientConnection client) {
        try {
            lock.lock();
            iterator = connectedClients.iterator();
            while (iterator.hasNext()) {
                if (!iterator.next().isAlive()) {
                    System.out.println("SOCKET VAR INTE ONLINE 0.0");
                    iterator.remove();
                }
            }
            connectedClients.add(client);
        } finally {
            lock.unlock();
        }
    }

    public void removeClient(ClientConnection client) {
        try {
            lock.lock();
            for (int i = 0; i < connectedClients.size(); i++) {
                if (connectedClients.get(i).equals(client)) {
                    connectedClients.remove(i);
                    for (ClientConnection connectedClient : connectedClients) {
                        System.out.println(connectedClient);
                    }
                    System.out.println("-----------------------------------------------------------------");
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public ArrayList<ClientConnection> getConnectedClients() {
        return connectedClients;
    }
}
