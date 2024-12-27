/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnectionmanager.Ui;

import dbconnectionmanager.db.DatabaseConnectorInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author dasun
 */
public class CommandPanel extends JPanel {

    private final JComboBox<String> dropdownSelector; // Dropdown for database selection
    private final JTextArea commandInputArea; // Input area for SQL commands
    private final HashMap<String, DatabaseConnectorInterface> databaseConnections; // Map for database connections
    private final RightPanel rightPanel; // Reference to the RightPanel for displaying results

    public CommandPanel(RightPanel rightPanel) {
        this.rightPanel = rightPanel; // Initialize RightPanel reference
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 70));

        // Initialize components
        commandInputArea = new JTextArea();
        commandInputArea.setLineWrap(true);
        commandInputArea.setWrapStyleWord(true);
        commandInputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JScrollPane scrollPane = new JScrollPane(commandInputArea);

        dropdownSelector = new JComboBox<>(new String[]{"Select database"});
        databaseConnections = new HashMap<>();

        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener(new ExecuteButtonListener());

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(dropdownSelector);
        actionPanel.add(executeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the dropdown with new database options and stores their
     * connections.
     *
     * @param databaseName The name of the database.
     * @param connector The connector for the database.
     */
    public void addDatabaseConnection(String databaseName, DatabaseConnectorInterface connector) {
        databaseConnections.put(databaseName, connector);
        dropdownSelector.addItem(databaseName);
    }

    /**
     * Listener for the Execute button to run SQL commands.
     */
    private class ExecuteButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedDatabase = (String) dropdownSelector.getSelectedItem();
            String command = commandInputArea.getText();

            if (selectedDatabase == null || selectedDatabase.equals("Select database")) {
                JOptionPane.showMessageDialog(
                        CommandPanel.this,
                        "Please select a database.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (command.isEmpty()) {
                JOptionPane.showMessageDialog(
                        CommandPanel.this,
                        "Please enter a SQL command.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            DatabaseConnectorInterface connector = databaseConnections.get(selectedDatabase);

            if (connector != null) {
                try {
                    // Determine query type and execute the corresponding method
                    String queryType = command.trim().split(" ")[0].toUpperCase(); // Get the first word (query type)

                    switch (queryType) {
                        case "SELECT":
                            // For SELECT queries
                            List<Object[]> selectData = connector.read(command);
                            // Extract column names
                            String[] columnNames;
                            if (!selectData.isEmpty()) {
                                columnNames = new String[selectData.get(0).length];
                                for (int i = 0; i < columnNames.length; i++) {
                                    columnNames[i] = "Column " + (i + 1); // Placeholder; could use metadata if needed
                                }
                            } else {
                                columnNames = new String[0];
                            }
                            // Convert List<Object[]> to 2D array
                            Object[][] selectDataArray = selectData.toArray(new Object[0][]);
                            SwingUtilities.invokeLater(() -> rightPanel.updateTableData(columnNames, selectDataArray));
                            break;

                        case "INSERT":
                            // For INSERT queries
                            int insertResult = connector.insert(command);
                            JOptionPane.showMessageDialog(
                                    CommandPanel.this,
                                    "Inserted " + insertResult + " row(s).",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            break;

                        case "UPDATE":
                            // For UPDATE queries
                            int updateResult = connector.update(command);
                            JOptionPane.showMessageDialog(
                                    CommandPanel.this,
                                    "Updated " + updateResult + " row(s).",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            break;

                        case "DELETE":
                            // For DELETE queries
                            int deleteResult = connector.delete(command);
                            JOptionPane.showMessageDialog(
                                    CommandPanel.this,
                                    "Deleted " + deleteResult + " row(s).",
                                    "Success",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            break;

                        default:
                            JOptionPane.showMessageDialog(
                                    CommandPanel.this,
                                    "Unsupported query type: " + queryType,
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            break;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            CommandPanel.this,
                            "Error executing query: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        CommandPanel.this,
                        "No connection available for the selected database.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

}
