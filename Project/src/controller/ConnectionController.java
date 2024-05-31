package controller;

import model.Recipe;
import model.User;

import java.util.HashMap;

import view.ClientConnection;
import model.ConnectedClients;
import view.ConnectionListener;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import static controller.Constants.*;

/**
 * This class is responsible for handling connected clients.
 *
 * @author Anton Jansson
 * @author Anton Persson
 */
public class ConnectionController {
    public UserController userController;
    private final RecipeController recipeController;
    private final ConnectedClients connectedClients;
    //TODO: The intentions might cause problems since they aren't Thread proof. I will look onto this after we've implemented the communication in the server

    /**
     * This constructor initiates instance variables
     *
     * @param userController
     * @param recipeController
     * @author Anton Jansson
     */
    public ConnectionController(UserController userController, RecipeController recipeController) {
        this.userController = userController;
        this.recipeController = recipeController;
        this.connectedClients = new ConnectedClients();
        new ConnectionListener(2343, this);
        new ConnectionListener(780, this);
    }

    /**
     * This method creates a new {@link ClientConnection} and then starts it as a thread. The {@link ClientConnection}
     * is then added to {@link ConnectedClients}. Lastly, the method sends all the recipes and users from the server
     * to the client.
     *
     * @param clientSocket A {@link Socket} that is sent to the constructor of {@link ClientConnection}
     * @author Anton Jansson
     * @author Anton Persson
     */
    public void establishClientsConnection(Socket clientSocket) {
        try {
            ClientConnection clientConnection = new ClientConnection(clientSocket, this);
            new Thread(clientConnection).start();
            connectedClients.addClient(clientConnection);
            sendRecipesOnStartUp(clientConnection); // This method sends the recipes to the client that has connected
            sendObjectToEveryClient(prepareListOfUsers(), S_SEND_ALL_USERS);
            sendIngredientsAtStartup(clientConnection);// This method sends the users to the client that has connected
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sends the list of {@link Recipe} from the server to the client in the form of an {@link ArrayList}
     * containing {@link Object}
     *
     * @param clientConnection The {@link ClientConnection} that is to get the recipes.
     * @author Anton Persson
     */
    public void sendRecipesOnStartUp(ClientConnection clientConnection) throws SQLException {
        recipeController.updateAllRecipes();
        ArrayList<Recipe> recipes = recipeController.getRecipes(); // Collect the recipes
        ArrayList<Object> recipesToSend = new ArrayList<>(recipes); // Convert to object list

        clientConnection.sendIntention(S_SEND_ALL_RECIPES);
        clientConnection.sendObject(recipesToSend);
    }


    /**
     * This method gets an {@link ArrayList} of {@link User} from the {@link UserController} and converts it into an
     * ArrayList of {@link Object} and later returns it.
     *
     * @return A list of users
     * @author Anton Persson
     */
    public ArrayList<Object> prepareListOfUsers() {
        ArrayList<User> users = userController.getAllUsers();
        ArrayList<Object> listToSend = new ArrayList<>(users);
        return listToSend;
    }

    /**
     * This method creates an {@link ArrayList} of {@link ClientConnection} from the clients in {@link ConnectedClients}.
     * The method then loops through the list and for each {@link ClientConnection}, an intention and {@link Object} is
     * sent.
     *
     * @param object
     * @param intention
     * @author Anton Persson
     */
    public void sendObjectToEveryClient(Object object, int intention) {
        ArrayList<ClientConnection> client = connectedClients.getConnectedClients();
        for (ClientConnection clientConnection : client) {
            clientConnection.sendIntention(intention);
            clientConnection.sendObject(object);
        }
    }

    public void sendIngredientsAtStartup(ClientConnection clientConnection) throws SQLException {
        ArrayList<Object> ingredientsToSend = recipeController.getIngredientsStartup();
        clientConnection.sendIntention(S_SEND_ALL_INGREDIENTS);
        clientConnection.sendObject(ingredientsToSend);
    }

    public void sendFavoriteRecipes(ArrayList<Recipe> recipes, int intention, ClientConnection clientConnection) {
        ArrayList<Object> recipesToSend = new ArrayList<>(recipes);
        clientConnection.sendIntention(C_USER_WANT_FAVORITES);
        clientConnection.sendObject(recipesToSend);
    }

    public void sendOwnRecipes(ArrayList<Recipe> recipes, int intention, ClientConnection clientConnection) {
        ArrayList<Object> recipesToSend = new ArrayList<>(recipes);
        clientConnection.sendIntention(intention);
        clientConnection.sendObject(recipesToSend);
    }

    /**
     * This method reveals the intention of a client and then does something based on the intention.
     *
     * @param clientConnection {@link ClientConnection} that the intention came from
     * @param intention        int that decides what happens
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     * @author Anton Persson
     * @author Heidi Wännmann
     */
    public synchronized void revealClientIntention(ClientConnection clientConnection, int intention) {
        switch (intention) {
            case C_WANTS_TO_DISCONNECT:
                clientConnection.setListenForObject(false);
                clientConnection.setListenForObject(false);
                clientConnection.closeConnection();
                connectedClients.removeClient(clientConnection);
                break;
            case C_WANT_TO_REGISTER:
                clientConnection.setListenForIntention(false);
                clientConnection.setListenForObject(true);
                break;
            case C_CREATE_RECIPE:
                clientConnection.setListenForIntention(false);
                clientConnection.setListenForObject(true);
                break;
            case C_USER_WANT_FAVORITES:
                clientConnection.setListenForIntention(false);
                clientConnection.setListenForObject(true);
                break;
            case C_USER_WANT_OWN_RECIPES:
                clientConnection.setListenForIntention(false);
                clientConnection.setListenForObject(true);
        }
    }

    /**
     * This method packs up an {@link Object} from a Client. Based on the intention in the server the Object is unpacked
     * differently.
     *
     * @param clientConnection The {@link ClientConnection} that has sent the {@link Object}
     * @param object           The {@link Object} that is to be unpacked
     * @author Anton Persson
     * @author Heidi Wänmann
     */
    public synchronized void packUpObject(ClientConnection clientConnection, int intention, Object object) throws SQLException {
        switch (intention) {
            case C_WANT_TO_REGISTER:
                User user = (User) object;
                userController.addUser(user);
                sendObjectToEveryClient(prepareListOfUsers(), S_UPDATE_C_LIST_OF_USERS);
                break;
            case C_CREATE_RECIPE:
                Recipe recipe = (Recipe) object;
                System.out.println(recipe.getRecipeName() + recipe.getInstructions() + recipe.getAuthor() + recipe.getImageOfRecipe() + recipe.getDish().getTypeOfFood());
                if (recipe.getImageOfRecipe() == null) {
                    System.out.println("Bilden följde inte med");
                }
                recipeController.createRecipe(recipe);
                break;
            case C_USER_WANT_FAVORITES:
                user = (User) object;
                ArrayList<Recipe> favoriteRecipes = userController.getUserFavoriteRecipes(user);
                sendFavoriteRecipes(favoriteRecipes, C_USER_WANT_FAVORITES, clientConnection);
                for (Recipe rrecipe : favoriteRecipes) {
                    System.out.println(rrecipe.getRecipeName());
                }
                break;
            case C_WANTS_TO_ADD_FAVORITE:
                HashMap<String, Object> favoriteRecipe = (HashMap<String, Object>) object;
                user = (User) favoriteRecipe.get("user");
                recipe = (Recipe) favoriteRecipe.get("recipe");
                userController.addFavoriteRecipe(recipe, user);
                break;
            case C_USER_WANT_OWN_RECIPES:
                user = (User) object;
                ArrayList<Recipe> ownRecipes = userController.getOwnRecipes(user);
                sendOwnRecipes(ownRecipes, C_USER_WANT_OWN_RECIPES, clientConnection);

        }
        clientConnection.setListenForObject(false);
        clientConnection.setListenForIntention(true);
    }
}