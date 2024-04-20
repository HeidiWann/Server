package view;

import controller.ConnectionController;

/**
 * This class manages connections form clients by setting up a listener.
 * It lets the ConnectionController deal with handling these connections.
 *
 * @author Salma Omar
 */

public class ClientConnection {
    private final ConnectionListerner connectionListerner;

    /**
     * Creates a new ClientConnection, passing in the ConnectionsController to handle incoming connections.
     * @param connectionController the ConnectionController responsible for managing incoming client connections.
     */

    public ClientConnection(ConnectionController connectionController) {
        this.connectionListerner = new ConnectionListerner(connectionController);
    }
}
