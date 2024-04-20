package controller;

import model.DatabaseCommunicator;
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
    private DatabaseCommunicator databaseCommunicator;


    /**
     * Clas constructor
     *
     * @param databaseCommunicator
     * @author Anton Jansson
     */
    public UserController(DatabaseCommunicator databaseCommunicator) {
        this.databaseCommunicator = databaseCommunicator;
    }
    /**
     * Method used for when a client registers to the database
     * @param user User
     * @author Anton Jansson
     */
    public void register(User user) {
        String query = " ";
        databaseCommunicator.executeQueryVoidReturn(query);
    }
    /**
     * Method used for when a user updates it profile
     * @param user User
     * @author Anton Jansson
     */
    public void updateProfile(User user) {
        String query = " ";
        databaseCommunicator.executeQueryVoidReturn(query);
    }

    /**
     * Method used for fetching users from the database
     * @return An ArrayList of users
     * @author Anton Jansson
     */

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        return users;
    }
}


