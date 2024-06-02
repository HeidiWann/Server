package controller;

import java.sql.SQLException;

/**
 * Main class for the server. It starts the server.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class Main {
    public static void main(String[] args) throws SQLException {
       ServerController serverController = new ServerController();
    }
}
