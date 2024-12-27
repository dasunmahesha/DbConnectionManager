/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnectionmanager.Ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author dasun
 */
public class ToolbarPanel extends JPanel {

    public ToolbarPanel(JFrame parentFrame, ExploreAreaPanel01 exploreAreaPanel,CommandPanel commandPanel) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btnAddDatabase = new JButton("Add Database");

        btnAddDatabase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDatabaseDialog dialog = new AddDatabaseDialog(parentFrame, exploreAreaPanel,commandPanel);
                dialog.setVisible(true);
            }
        });

        add(btnAddDatabase);
    }
}