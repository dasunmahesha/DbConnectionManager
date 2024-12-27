/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnectionmanager.Ui;

import javax.swing.*;
import java.awt.*;
import dbconnectionmanager.db.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dasun
 */
public class AddDatabaseDialog extends JDialog {

    private final ExploreAreaPanel01 exploreAreaPanel;
    private final CommandPanel commandPanel; 

    public AddDatabaseDialog(JFrame parentFrame, ExploreAreaPanel01 exploreAreaPanel, CommandPanel commandPanel) {
        super(parentFrame, "Add Database", true);
        this.exploreAreaPanel = exploreAreaPanel;
        this.commandPanel = commandPanel; 
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JLabel lblServer = new JLabel("Select Database Server:");
        String[] serverOptions = {"MySQL", "MSSQL", "MariaDB"};
        JComboBox<String> cmbServer = new JComboBox<>(serverOptions);
        panel.add(lblServer);
        panel.add(cmbServer);

        JLabel lblhostName = new JLabel("Enter hostaddress:");
        JTextField txthostName = new JTextField("localhost");
        panel.add(lblhostName);
        panel.add(txthostName);

        JLabel lblportNumber = new JLabel("Enter portnumber:");
        JTextField txtportNumber = new JTextField();
        panel.add(lblportNumber);
        panel.add(txtportNumber);

        cmbServer.addActionListener(e -> {
            String selectedServer = (String) cmbServer.getSelectedItem();
            String portNumber;

            switch (selectedServer) {
                case "MySQL":
                    portNumber = "3306";
                    break;
                case "MSSQL":
                    portNumber = "1433";
                    break;
                case "MariaDB":
                    portNumber = "5508";
                    break;
                default:
                    portNumber = "Unknown";
                    break;
            }
            txtportNumber.setText(portNumber);

        });
        JLabel lblDbName = new JLabel("Enter Database Name:");
        JTextField txtDbName = new JTextField();
        panel.add(lblDbName);
        panel.add(txtDbName);

        JLabel lblUsername = new JLabel("Enter Username:");
        JTextField txtUsername = new JTextField();
        panel.add(lblUsername);
        panel.add(txtUsername);

        JLabel lblPassword = new JLabel("Enter Password:");
        JPasswordField txtPassword = new JPasswordField();
        panel.add(lblPassword);
        panel.add(txtPassword);

        JButton btnAddDatabase = new JButton("Add Database");
        JButton btnCancel = new JButton("Cancel");

        btnAddDatabase.addActionListener(e -> {
            String hostaddress = txthostName.getText();
            String portnumber = txtportNumber.getText();
            String dbName = txtDbName.getText();
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String server = (String) cmbServer.getSelectedItem();

            DatabaseConnectorInterface connector;
            try {
                
                switch (server) {
                    case "MySQL":
                        connector = new MySQLDatabaseConnector("jdbc:mysql://" + hostaddress + ":" + portnumber + "/" + dbName, username, password);
                        break;
                    case "MSSQL":
                        connector = new MSSQLDatabaseConnector("jdbc:sqlserver://" + hostaddress + ":" + portnumber + ";databaseName=" + dbName, username, password);
                        break;
                    case "MariaDB":
                        connector = new MariaDBDatabaseConnector("jdbc:mysql://" + hostaddress + ":" + portnumber + "/", dbName, username, password);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Unsupported database server.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (connector.getConnection() != null) {
                    JOptionPane.showMessageDialog(this, "Connection successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    List<String> tables = connector.getAllTables();
                    exploreAreaPanel.addDatabase(server, dbName, tables.toArray(new String[0]), connector);
                    commandPanel.addDatabaseConnection(dbName, connector);
                } else {
                    JOptionPane.showMessageDialog(this, "Connection failed. Please check your details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            }
        });

        btnCancel.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddDatabase);
        buttonPanel.add(btnCancel);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(parentFrame);
    }
}
