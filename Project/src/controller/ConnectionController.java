package controller;

import model.ClientConnection;
import model.Recipe;
import model.User;
import view.ConnectionListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;

import static controller.Constans.*;


/**
 * This class is responsible for handling connected clients. The class contains an inner class, "ClientHandler"
 * and it extends thread. Each connected client is given its own Clienthandler.
 */
public class ConnectionController {
    public UserController userController;
    private ConnectionListener connectionListener;
    private RecipeController recipeController;
    private ClientConnection clientConnection;

    /**
     * Class constructor.
     *
     * @param
     * @author Anton Jansson
     */

    public ConnectionController(UserController userController, RecipeController recipeController) {
        this.userController = userController;
        this.recipeController = recipeController;
        this.connectionListener = new ConnectionListener(2343, this);

    }

    public void clientConnectionHandler(Socket clientSocket) {
        try {
            ClientConnection clientConnection = new ClientConnection(clientSocket, this);
            new Thread(clientConnection).start();
        } catch (IOException e) {
            System.out.println("IOExcetion in clientConnectionHandler");
            e.printStackTrace();
        }
    }

    private void sendUserData(ObjectOutputStream oos) throws SQLException, IOException {
        HashMap<User, ClientConnection> listOfUsers = userController.getNewUserInfo();
        oos.writeObject(listOfUsers);
    }

    private void sendRecipeData(ObjectOutputStream oos) throws SQLException, IOException {
        HashMap<Recipe, ClientConnection> listOfRecipes = recipeController.getNewRecipeInfo();
        oos.writeObject(listOfRecipes);
    }

    public void newConnection(ObjectOutputStream newClientOutputStream, Object dataObject) {
        try {
            if (dataObject instanceof User) {
                sendUserData(newClientOutputStream);
            } else if (dataObject instanceof Recipe) {
                sendRecipeData(newClientOutputStream);
            } else {
                System.out.println("Unsupported data object provided.");
            }
        } catch (IOException e) {
            System.err.println("Failed to write object: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            throw new RuntimeException("Database error", e);
        }
    }

    public synchronized void revealClientIntention(int intention, ClientConnection clientConnection, Object object) throws IOException, ClassNotFoundException, SQLException {
        ObjectOutputStream oos = clientConnection.getObjectOutputStream();
        ObjectInputStream ois = clientConnection.getObjectInputStream();

        if (object != null) {
            handleObject(object, oos);
        } else if (intention != OK) {
            handleIntention(intention, ois, oos);
        } else {
            System.out.println("No valid object or intention provided.");
        }
    }

    private void handleObject(Object object, ObjectOutputStream oos) throws IOException {
        try {
            if (object instanceof User) {
                sendUserData(oos);  // Antag att denna metod hanterar användarobjektet
            } else if (object instanceof Recipe) {
                sendRecipeData(oos);  // Antag att denna metod hanterar receptobjektet
            } else {
                System.out.println("The object can´t be identified");
            }
        } catch (IOException | SQLException e) {
            System.err.println("Failed to send object: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void handleIntention(int intention, ObjectInputStream ois, ObjectOutputStream oos) throws IOException, ClassNotFoundException, SQLException {
        switch (intention) {
            case C_WANTS_TO_DISCONNECT:
                clientConnection.closeConnection();
                break;
            case C_HAVE_A_OBJECT:
                Object object = ois.readObject();
                if (object instanceof Recipe) {
                    sendRecipeData(oos);
                }
                break;
            case C_GET_USER_INFO:
                User user = userController.getUserFromObject(ois);
                oos.writeObject(user);
                break;
            default:
                System.out.println("Don't know what intention this: " + intention + " wants to do");
                break;
        }
    }

}

