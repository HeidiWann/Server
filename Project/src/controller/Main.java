package controller;


import model.DatabaseCommunicator;
import model.DatabaseConnection;

import java.sql.SQLException;

/**
 * Main class for the server. It starts the server.
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        //DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();

        //DatabaseConnection dbController = new DatabaseConnection();
        //ServerController serverController = new ServerController();

    ScrapinController scrapinController=new ScrapinController();
    scrapinController.scrapeAllStores();

    }
}


