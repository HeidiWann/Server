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

    public void register() {
    }
    public void updateProfile() {
    }

   public ArrayList<User> getNewConnectionInfo() {
        String getNewConnectionInfoQuerry="";
        dbController.executeQuery(getNewConnectionInfoQuerry);
        return new ArrayList<>();
    }
}


