package controller;

import model.DatabaseCommunicator;
import model.Recipe;
import model.User;
import view.ClientConnection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that starts the whole server. This includes all controllers.
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class ServerController {

    private DatabaseCommunicator databaseCommunicator;
    private ClientConnection clientConnection;
    private DatabaseController databaseController;
    private RecipeController recipeController;
    private UserController userController;
    private ConnectionController connectionController;


    /**
     * Clas constructor. It starts a few controllers
     *
     * @author Anton Jansson
     * @author Heidi Wännman
     */
    public ServerController() throws SQLException {



        this.databaseController = new DatabaseController();
        this.recipeController = new RecipeController(databaseController);

        this.connectionController = new ConnectionController(this);



    }
    public void establishConnection() throws SQLException {
        databaseCommunicator.getDatabaseconnection();
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
        ArrayList<User> listOfUsers = userController.getAllUsers();
        try {
            newClientOutputStream.writeObject(listOfRecipes);
            newClientOutputStream.writeObject(listOfUsers);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}




