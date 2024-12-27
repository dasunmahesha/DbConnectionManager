/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnectionmanager.Ui;

import dbconnectionmanager.db.DatabaseConnectorInterface;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

/**
 *
 * @author dasun
 */
public class RightPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public RightPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTableData(String[] columnNames, Object[][] data) {
        System.out.println("Data to display:");
        for (Object[] row : data) {
            System.out.println(Arrays.toString(row));
        }
        tableModel.setDataVector(data, columnNames);
        table.revalidate();
        table.repaint();
    }

    public void clearTableData() {
        tableModel.setDataVector(new Object[0][0], new String[0]);
        table.revalidate();
        table.repaint();
    }

    public void selectDataFromTable(DatabaseConnectorInterface connector, String tableName) {
        try {
            var data = connector.selectDataFromTable(tableName);  

            if (!data.isEmpty()) {
                String[] columnNames = new String[data.get(0).length];
                for (int i = 0; i < columnNames.length; i++) {
                    columnNames[i] = "Column " + (i + 1);  
                }

                Object[][] dataArray = data.toArray(new Object[0][]);

                updateTableData(columnNames, dataArray);
            } else {
                clearTableData();
                JOptionPane.showMessageDialog(this, "No data found in the table.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
