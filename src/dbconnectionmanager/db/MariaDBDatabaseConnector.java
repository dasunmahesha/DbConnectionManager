/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnectionmanager.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dasun
 */

public class MariaDBDatabaseConnector implements DatabaseConnectorInterface {

    private String url;
    private String username;
    private String password;
    private Connection connection;

    /**
     * Constructor for MariaDBDatabaseConnector
     *
     * @param host The hostname or IP address of the MariaDB server
     * @param databaseName The name of the database to connect to
     * @param username The username for authentication
     * @param password The password for authentication
     */
    public MariaDBDatabaseConnector(String host, String databaseName, String username, String password) {
        
        this.url = host + databaseName;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        try {
            // Load MariaDB JDBC Driver (same as MySQL)
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("MariaDB Connection Error: " + e.getMessage());
        }
        return connection;
    }

    @Override
    public List<String> getAllTables() throws Exception {
        List<String> tables = new ArrayList<>();
        if (connection == null) {
            throw new Exception("Connection is not established.");
        }

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SHOW TABLES");

        while (resultSet.next()) {
            tables.add(resultSet.getString(1));
        }
        return tables;
    }

    @Override
    public int insert(String query) throws Exception {
        if (connection == null) {
            throw new Exception("Connection is not established.");
        }

        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        }
    }

    @Override
    public int update(String query) throws Exception {
        return insert(query); // UPDATE is similar to INSERT
    }

    @Override
    public int delete(String query) throws Exception {
        return insert(query); // DELETE is similar to INSERT
    }

    @Override
    public List<Object[]> read(String query) throws Exception {
        if (connection == null) {
            throw new Exception("Connection is not established.");
        }

        List<Object[]> rows = new ArrayList<>();
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                rows.add(row);
            }
        }

        return rows;
    }
    @Override
    public List<Object[]> selectDataFromTable(String tableName) throws Exception {
        String query = "SELECT * FROM " + tableName;
        return read(query);  
    }
}