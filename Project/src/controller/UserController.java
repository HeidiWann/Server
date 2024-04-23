package controller;

import model.ClientConnection;
import model.DatabaseCommunicator;
import model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Clas responsible for handling logic related to users
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class UserController {
    private DatabaseCommunicator databaseCommunicator;
    private DatabaseController databaseController;
    private HashMap<User, ClientConnection> userConnected = new HashMap<>();

    /**
     * Clas constructor
     *
     * @param databaseCommunicator
     * @author Anton Jansson
     */
    public UserController(DatabaseCommunicator databaseCommunicator) {
        this.databaseCommunicator = databaseCommunicator;
    }
    public  HashMap<User, ClientConnection> getNewUserInfo() throws SQLException {

        HashMap<User, ClientConnection> users = new HashMap<>();
        ArrayList<User> userList = databaseController.getAllUsers();

        for (User user : userList) {
            users.put(user, null);
        }
        return users;
    }
    public User getUserFromObject(Object object) {
        if (object instanceof User) {
            return (User) object;
        }
        System.out.println("Received object is not a user");
        return null;
    }

    public void userConnectionHandler(User user, ClientConnection clientConnection) {
        userConnected.put(user, clientConnection);
    }

    public void userDisconnecter(User user) {
        userConnected.remove(user);
    }

}



