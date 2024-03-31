package controller;

import model.Recipe;
import model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Class that starts the whole server. This includes all controllers.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class ServerController {
    private RecipeController recipeController;
    private UserController userController;
    private ConnectionController connectionController;

    /**
     * Clas constructor. It starts a few controllers
     *
     * @author Anton Jansson
     * @author Heidi Wännman
     */
    public ServerController() {
        DatabaseController dbController = new DatabaseController();
        recipeController = new RecipeController(dbController);
        userController = new UserController(dbController);
        connectionController = new ConnectionController(this);

    }

    /**
     * Method used for when a client connects to the server
     *
     * @param newClientOutputStream
     * @author Anton Jansson
     */

    /*TODO flytta oos till att hanteras för den clientens handler istället.. SOLID
    todo Förslagsvis med en metod i hanlder som hanterar skickande av data
     */
    public void newConnection(ObjectOutputStream newClientOutputStream) {
        ArrayList<Recipe> listOfRecipes = recipeController.getNewConnectionInfo();
        ArrayList<User> listOfUsers = userController.getNewConnectionInfo();
        try {
            newClientOutputStream.writeObject(listOfRecipes);
            newClientOutputStream.writeObject(listOfUsers);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}




