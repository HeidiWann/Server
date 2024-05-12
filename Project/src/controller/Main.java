package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;

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

        ArrayList<Recipe> recipes = dbController.getRecipes();
        for(Recipe recipe : recipes){
            System.out.println(recipe.toString());
        }

        //WebScraping webScraping = new WebScraping();
        // webScraping.scrapeICAForNameAndPrice("https://handlaprivatkund.ica.se/stores/1003937/categories/mejeri-ost/7719dd17-9048-4055-ac32-56b5533a4ca7");
    }

}
