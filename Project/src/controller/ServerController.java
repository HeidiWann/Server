package controller;

import model.Recipe;
import model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Class that starts the whole server. This includes all controllers.
 */
public class ServerController {
    private RecipeController recipeController;
    private UserController userController;
    private ConnectionController connectionController;

    public ServerController() {
        DatabaseController dbController = new DatabaseController();
        recipeController = new RecipeController(dbController);
        userController = new UserController(dbController);
        connectionController = new ConnectionController(this);

    }

    public void newConnection(ObjectOutputStream newClientOutputStream) {
        ArrayList<Recipe> listOfRecipes=recipeController.getNewConnectionInfo();
        ArrayList<User> listOfUsers=userController.getNewConnectionInfo();;


        try {
            newClientOutputStream.writeObject(listOfRecipes);
            newClientOutputStream.writeObject(listOfUsers);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}




