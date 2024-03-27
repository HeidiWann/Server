package controller;

import model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
}


