package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Main class for the server. It starts the server.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseCommunicator databaseCommunicator = new DatabaseCommunicator();
        DatabaseConnection dbconn = new DatabaseConnection(databaseCommunicator);
        DatabaseController dbController = new DatabaseController(databaseCommunicator);
        ServerController serverController = new ServerController();



        //TODO endast för testing .. behövöer ett bättre sätt att starta scraping @jansson
        /*ScrapinController scrapinController=new ScrapinController();
        scrapinController.scrapeAllStores();
        */
    }

}
