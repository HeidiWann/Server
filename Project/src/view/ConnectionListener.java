package view;
import controller.ConnectionController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class that listens for new connections and sends connections forward to the ConnectionController
 *
 * @author Anton Jansson
 */
public class ConnectionListener extends Thread {
    private ServerSocket serverSocket;
    private ConnectionController connectionController;

    public ConnectionListener(int port, ConnectionController connectionController) {
        this.connectionController = connectionController;
        try {
            serverSocket = new ServerSocket(port);
            start();
        } catch (IOException e) {
            System.out.println("IOExcetion in constructor of ConnectionListener");
            System.out.println(e.getMessage());
        }
    }
    @Override
    public synchronized void run() {
        while (!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                connectionController.establishClientsConnection(socket);
            } catch (IOException ioe) {
                System.out.println("Could not accept the clients connection");
                throw new RuntimeException();
            }
        }
    }
}
