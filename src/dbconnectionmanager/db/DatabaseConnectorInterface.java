/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dbconnectionmanager.db;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author dasun
 */
public interface DatabaseConnectorInterface {

    Connection getConnection();

    List<String> getAllTables() throws Exception;

    int insert(String query) throws Exception;

    int update(String query) throws Exception;

    int delete(String query) throws Exception;

    List<Object[]> read(String query) throws Exception;

    List<Object[]> selectDataFromTable(String tableName) throws Exception;
}
