package model;


import java.sql.*;

/**
 * Class responsible for communicating with the database
 *
 * @author Anton Jansson
 */
public class DatabaseCommunicator {


    private DatabaseConnection databaseConnection;

    public DatabaseCommunicator(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * A generic method for executing querys.
     *
     * @param query String
     * @return void
     * @author Anton Jansson
     */
    public void executeQueryVoidReturn(String query) {
    }

    /**
     * A generic method for retrieving resultsets from the database.
     *
     * @param query String
     * @return ResultSet
     * @author Anton Jansson
     */
    public ResultSet getResultSet(String query) {
        try (Connection connection = databaseConnection.getDatabaseconnection(); Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


    public Statement createStatement() throws SQLException {
        return databaseConnection.getDatabaseconnection().createStatement();

    }

    public PreparedStatement prepareStatement(String query) throws SQLException {

        return databaseConnection.getDatabaseconnection().prepareStatement(query);
    }
}
