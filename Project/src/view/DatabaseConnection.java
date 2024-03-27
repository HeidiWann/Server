package view;

import controller.DatabaseController;

import java.sql.Connection;

public class DatabaseConnection {
    private DatabaseController dbController;
    private String url;
    private String user;
    private String password;

    public DatabaseConnection(DatabaseController dbController) {
        this.dbController=dbController;
    }

    public Connection connect(String url, String user, String password) {
       return null;
    }

    //Todo är samma som connect?
    public  void getConnection(){

    }
    public void endConnection(){

    }
}



