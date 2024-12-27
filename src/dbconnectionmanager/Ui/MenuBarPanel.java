/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnectionmanager.Ui;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author dasun
 */
public class MenuBarPanel extends JMenuBar{
    public MenuBarPanel(JFrame parentFrame) {
        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem exitItem = new JMenuItem("Exit");

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); 
            }
        });

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.addSeparator(); 
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("Edit");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");

        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parentFrame, "Database Connector \nDeveloped by Dasun - SOF/20/B1/41 \n tested with \n Mysql \n Mariadb \n databse servers.");
            }
        });

        helpMenu.add(aboutItem);

        add(fileMenu);
        add(editMenu);
        add(helpMenu);
    }
}
