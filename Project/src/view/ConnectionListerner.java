package view;

import controller.ConnectionController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class that listens for new connections and gives these connections a separate handler
 */
public class ConnectionListerner extends Thread {

    private ServerSocket serverSocket;
    private ConnectionController connectionController;
    private final int portNbr = 780;

    public ConnectionListerner(ConnectionController connectionController) {
        try {
            serverSocket = new ServerSocket(portNbr);
        } catch (IOException ioe) {
            throw new RuntimeException();
        }

        this.connectionController = connectionController;

        this.start();
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                Socket socket = serverSocket.accept();
                sendNewSocket(socket);
            } catch (IOException ioe) {
                throw new RuntimeException();
            }
        }
    }

    public void sendNewSocket(Socket socket) throws IOException {
        connectionController.newConnection(socket);
    }


}
