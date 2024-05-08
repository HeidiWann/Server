package controller;

import model.DatabaseCommunicator;
import model.DatabaseConnection;
import java.sql.SQLException;

/**
 * Main class for the server. It starts the server.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();
        DatabaseConnection dbConn = new DatabaseConnection(databaseCommunicator);
        DatabaseController dbController = new DatabaseController(databaseCommunicator);
        ServerController serverController = new ServerController();
        //WebScraping webScraping = new WebScraping();
        // webScraping.scrapeICAForNameAndPrice("https://handlaprivatkund.ica.se/stores/1003937/categories/mejeri-ost/7719dd17-9048-4055-ac32-56b5533a4ca7");
    }
}


