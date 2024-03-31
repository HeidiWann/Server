package controller;


import model.User;
import view.ConnectionListerner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;


/**
 * This class is responsible for handling connected clients. The class contains an inner class, "ClientHandler"
 * and it extends thread. Each connected client is given its own Clienthandler.
 */
public class ConnectionController {
    private ServerController serverController;
    private ConnectionListerner connectionListerner;

    private List<ClientHandler> synchronizedClientHandlers;


    /**
     * Class constructor.
     *
     * @param serverController
     * @author Anton Jansson
     */
    public ConnectionController(ServerController serverController) {
        this.serverController = serverController;
        connectionListerner = new ConnectionListerner(this);
        List<ClientHandler> clientHandlers = new ArrayList<>();
        synchronizedClientHandlers = Collections.synchronizedList(clientHandlers);

    }


    /**
     * Method that creates new Clienthandlers and gives them to the newly connected
     * clients. It also starts the process for giving these new clients some data
     *
     * @param socket
     * @throws IOException
     * @author Anton Jansson
     */
    public void newConnection(Socket socket) throws IOException {
        ClientHandler newClientHandler = new ClientHandler(socket);
        addClientHandler(newClientHandler);
        newClientHandler.start();

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        serverController.newConnection(oos);


    }

    private synchronized void addClientHandler(ClientHandler clientHandler) {
        synchronizedClientHandlers.add(clientHandler);
    }

    /**
     * Class responsible for handling a specific client connection
     *
     * @author Anton Jansson
     */
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
                    Object objectFromClient = ois.readObject();
                    if (objectFromClient != null) {
                        newRequest(objectFromClient);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

        }

        /**
         * Method used for when the client sends an object to the server through
         * the stream.
         *
         * @param object
         * @author Anton Jansson
         */
        private void newRequest(Object object) {
            if (object instanceof User) {
                if (user == null) {
                    this.user = (User) object;
                    addClientHandler(this);
                }
            }
        }

        /**
         * Method that returns the handlers client as a User object
         *
         * @return User user
         * @author Anton Jansson
         */
        public User getUser() {
            return this.user;
        }

    }
}
