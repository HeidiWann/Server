package controller;

import model.User;

import java.util.ArrayList;

/**
 * Clas responsible for handling logic related to users
 *
 * @author Anton Jansson
 * @author Heidi Wännman
 */
public class UserController {
    private User user;
    private DatabaseController dbController;

    /**
     * Clas constructor
     *
     * @param dbController
     * @author Anton Jansson
     */
    public UserController(DatabaseController dbController) {
        this.dbController = dbController;
    }

    /**
     * @author Heidi Wännman
     */
    public void login() {
    }

    /**
     * Method used for when a client registers to the database
     * @param user User
     * @author Anton Jansson
     */
    public void register(User user) {
        String query = " ";
        dbController.executeQueryVoidReturn(query);
    }
    /**
     * Method used for when a user updates it profile
     * @param user User
     * @author Anton Jansson
     */
    public void updateProfile(User user) {
        String query = " ";
        dbController.executeQueryVoidReturn(query);
    }

    /**
     * Method used for fetching users from the database
     * @return An ArrayList of users
     * @author Anton Jansson
     */
    public ArrayList<User> getNewConnectionInfo() {
        return dbController.getUsersForNewConnection();

    }
}


