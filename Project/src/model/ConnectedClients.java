package model;
import view.ClientConnection;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
public class ConnectedClients {
    private ArrayList<ClientConnection> connectedClients;
    private ReentrantLock lock;
    public ConnectedClients() {
        this.connectedClients = new ArrayList<>();
        lock = new ReentrantLock(true);
    }
    public void addClient(ClientConnection client) {
        try {
            lock.lock();
            connectedClients.add(client);
            for (ClientConnection connectedClient : connectedClients) {
                System.out.println(connectedClient);
            }
            System.out.println("-----------------------------------------------------------------");
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
