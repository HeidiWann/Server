package controller;

import model.DatabaseCommunicator;
import model.Ingredient;
import view.ClientConnection;
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
     * Clas constructor. It starts a few controllers
     *
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
        //this. scrapinController=new ScrapinController();
        //terminalDialog();
    }

    public void startWebScrapingAndAddToDatabase(){
        ArrayList<Ingredient> scrapedIngredients=scrapinController.scrapeAllStores();

        //TODO en test print loop
        for (Ingredient ingredient:scrapedIngredients){
            System.out.println(ingredient.getIngredientName()+" "+ingredient.getIngredientCost() + " "+ ingredient.getCategory()+" "+ ingredient.getSubCategory());
        }
        /*
        for (Ingredient ingredient:scrapedIngredients){
            databaseController.addIngredient(ingredient);
        }
         */
    }

    private void terminalDialog(){
        System.out.println("Start scraping? (type \"yes\" to start)");
        Scanner scanner = new Scanner(System.in);
        String input=scanner.nextLine();
        input.toLowerCase();
        if (input.equals("yes")){
            startWebScrapingAndAddToDatabase();
        }
    }
}