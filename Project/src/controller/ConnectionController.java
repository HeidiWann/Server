package controller;



import model.User;
import view.ConnectionListerner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ConnectionController {
    private ServerController serverController;
    private ConnectionListerner connectionListerner;

    private List<ClientHandler> synchronizedClientHandlers;


    public ConnectionController(ServerController serverController) {
        this.serverController = serverController;
        connectionListerner = new ConnectionListerner(this);
        List<ClientHandler> clientHandlers = new ArrayList<>();
        synchronizedClientHandlers = Collections.synchronizedList(clientHandlers);

    }


    public void newConnection(Socket socket) throws IOException {
        ClientHandler newClientHandler = new ClientHandler(socket);
        addClientHandler(newClientHandler);
        newClientHandler.start();

        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
        serverController.newConnection(oos);



    }

    private synchronized void addClientHandler(ClientHandler clientHandler) {
        synchronizedClientHandlers.add(clientHandler);
    }

    public class ClientHandler extends Thread {
        private ObjectInputStream ois;
        private User user;

        public ClientHandler(Socket socket) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (!interrupted()) {
                try {
                    newRequest(ois.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        private void newRequest(Object object) {
            if (object instanceof User) {
                if (user == null) {
                    this.user = (User) object;
                    addClientHandler(this);
                }
            }
        }

        public User getUser() {
            return this.user;
        }

    }
}
