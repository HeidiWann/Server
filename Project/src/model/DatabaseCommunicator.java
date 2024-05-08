package model;
import java.sql.*;


/**
 * Class responsible for communicating with the database
 *
 * @author Anton Jansson
 */
public class DatabaseCommunicator {
    private DatabaseConnection databaseConnection;

    public DatabaseCommunicator() throws SQLException {
        this.databaseConnection = new DatabaseConnection(this);
    }

    public Connection getDatabaseconnection() throws SQLException {
        return databaseConnection.getDatabaseconnection();
    }

    /**
     * A generic method for executing querys.
     *
     * @param query String
     * @return void
     * @author Anton Jansson
     */
    public void executeUpdate(String query, Object[] params) throws SQLException {
        try (Connection connection = databaseConnection.getDatabaseconnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            preparedStatement.executeUpdate();
        }
    }

    /**
     * A generic method for retrieving resultsets from the database.
     *
     * @param query String
     * @return ResultSet
     * @author Anton Jansson
     */
    public ResultSet getResultSet(String query) throws SQLException {
        try (Connection connection = databaseConnection.getDatabaseconnection();
             Statement statement = connection.createStatement()) {
            return statement.executeQuery(query);
        } finally {
            Statement statement = null;
            statement.close();
        }
    }
}
