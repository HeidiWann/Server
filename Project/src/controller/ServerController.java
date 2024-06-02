package controller;

import model.DatabaseCommunicator;
import model.Ingredient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that starts the whole server. This includes all controllers.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class ServerController {
    private DatabaseController databaseController;
    private RecipeController recipeController;
    private UserController userController;
    private ConnectionController connectionController;
    private DatabaseCommunicator databaseCommunicator;
    private ScrapinController scrapinController;


    /**
     * Clas constructor. It starts the server by starting a few controllers
     *
     * @throws SQLException if a database error occurs.
     * @author Anton Jansson
     * @author Heidi Wännman
     */
    public ServerController() throws SQLException {
        this.databaseCommunicator = new DatabaseCommunicator();
        databaseCommunicator.getDatabaseconnection();
        this.databaseController = new DatabaseController(databaseCommunicator);
        this.userController = new UserController(databaseCommunicator, databaseController);
        this.recipeController = new RecipeController(databaseController);
        this.connectionController = new ConnectionController(userController, recipeController);
        this.scrapinController = new ScrapinController();
        terminalDialog();
    }

    /**
     * Method that starts the web scraping process and adds the scraped Objects to the database.
     *
     * @author Anton Jansson
     */
    public void startWebScrapingAndAddToDatabase() {
        System.out.println("Scraping started");
        ArrayList<Ingredient> scrapedIngredients = scrapinController.scrapeAllStores();
        System.out.println("-----------------------------\nAdd to database started");

        for (Ingredient ingredient : scrapedIngredients) {
            System.out.println(ingredient.getIngredientName() + " is added to the database");
            databaseController.addIngredient(ingredient);
        }
        System.out.println("-----------------------------\nAdd to database done\n");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        terminalDialog();
    }

    /**
     * Method that starts the terminal dialog. This is done by creating a new Thread that is responsible for handling
     * the communication to the terminal user.
     *
     * @author Anton Jansson
     */
    private void terminalDialog() {
        Thread terminalDialogThread = new Thread(new TerminalDialog());
        terminalDialogThread.setName("terminalDialogThread");
        terminalDialogThread.start();
    }

    /**
     * Class that has the logic for handling input from the terminal user. The class implements Runnable
     *
     * @Anton Jansson
     */
    private class TerminalDialog implements Runnable {
        @Override
        public void run() {
            boolean scrapingStarted = false;
            do {
                System.out.println("Start scraping? (type \"yes\" to start)");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                input = input.toLowerCase();
                if (input.equals("yes")) {
                    startWebScrapingAndAddToDatabase();
                    scrapingStarted = true;
                } else {
                    System.out.println("Scraping not started");
                }
            } while (!scrapingStarted);
        }
    }
}