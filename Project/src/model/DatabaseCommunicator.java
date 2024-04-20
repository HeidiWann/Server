package model;


import java.sql.*;

/**
 * Class responsible for communicating with the database
 *
 * @author Anton Jansson
 */
public class DatabaseCommunicator {


    private DatabaseConnection databaseConnection;
    private Connection connection;

    public DatabaseCommunicator() throws SQLException { //fått ta bort så länge
        getDatabaseconnection();
    }

    public Connection getDatabaseconnection() throws SQLException {
        this.databaseConnection = new DatabaseConnection();
        return databaseConnection.getDatabaseconnection();
    }
    public void closeConnection() throws SQLException {
        databaseConnection.endDatabaseConnection(connection);


    }

    /**
     * A generic method for executing querys.
     *
     * @param query String
     * @return void
     * @author Anton Jansson
     */
    public void executeQueryVoidReturn(String query) {
        try (Connection connection = databaseConnection.getDatabaseconnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.err.println("Databasfel, det gick inte att utföra operationen: " + e.getMessage());
            throw new RuntimeException("Det gick inte att utföra en operation i databasen", e);
        }
    }
    /*public void executeQueryVoidReturn(String query) {
    }*/

    /**
     * A generic method for retrieving resultsets from the database.
     *
     * @param query String
     * @return ResultSet
     * @author Anton Jansson
     */
    public ResultSet getResultSet(String query) throws SQLException {
        Connection connection = databaseConnection.getDatabaseconnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
        // Stäng inte connection här för om det görs kan den inte hantera "resources" och om de stängs kan den inte göra det.
    }

    /*public ResultSet getResultSet(String query) {
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
    }*/
}
