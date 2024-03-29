package controller;

import model.User;

import java.util.ArrayList;

public class UserController {
    private User user;
    private DatabaseController dbController;

    public UserController(DatabaseController dbController) {
        this.dbController = dbController;
    }
    public void login() {
    }

    public void register(User user) {
        String query = " ";
        dbController.executeQuery(query);
    }
    public void updateProfile(User user) {
        String query = " ";
        dbController.executeQuery(query);
    }

    public ArrayList<User> getNewConnectionInfo() {
        return dbController.getUsersForNewConnection();

    }
}


