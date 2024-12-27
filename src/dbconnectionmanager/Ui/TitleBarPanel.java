/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnectionmanager.Ui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author dasun
 */
public class TitleBarPanel extends JPanel {

    public TitleBarPanel() {
        setLayout(new GridLayout(1, 1));
        setPreferredSize(new Dimension(0, 50));

        JLabel titleLabel = new JLabel("Database Connector - Assignment-04", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(titleLabel);
    }
}
