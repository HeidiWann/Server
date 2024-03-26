package view;

import java.sql.Connection;

public class DatabaseConnection {
    private String url;
    private String user;
    private String password;

    public DatabaseConnection() {
    }

    public Connection connect(String url, String user, String password) {

        return null;
    }

    public void disconnect(Connection connection) {
    }
}
