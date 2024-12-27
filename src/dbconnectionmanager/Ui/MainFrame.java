/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconnectionmanager.Ui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author dasun
 */
public class MainFrame {

    public void GuiAssignment() {
        // Declare all variables at the top
        JFrame frame;
        TitleBarPanel titleBar;
        MenuBarPanel menubarPanel;
        RightPanel rightPanel;
        ExploreAreaPanel01 exploreArea01;
        ToolbarPanel toolbarPanel;
        JPanel panel04, leftPanel, footerPanel, mainContainer;
        ExploreAreaPanel02 exploreArea02;
        CommandPanel commandPanel;
        JSplitPane splitPane;
        JLabel footerLabel;

        // Initialize components

        // Frame setup
        frame = new JFrame("Database Connector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Add Title Bar
        titleBar = new TitleBarPanel();

        // Add Menu Bar
        menubarPanel = new MenuBarPanel(frame);

        // Initialize RightPanel and ExploreAreaPanel01
        rightPanel = new RightPanel();
        exploreArea01 = new ExploreAreaPanel01(rightPanel);
        
        commandPanel = new CommandPanel(rightPanel);

        // Initialize Toolbar Panel
        toolbarPanel = new ToolbarPanel(frame, exploreArea01, commandPanel);

        // Panel for left and right sections
        panel04 = new JPanel();
        panel04.setLayout(new BorderLayout());

        // Left Panel containing two exploration panels
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1));
        leftPanel.setPreferredSize(new Dimension(150, 0));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add exploration panels to left panel
        leftPanel.add(exploreArea01);
        exploreArea02 = new ExploreAreaPanel02();
        leftPanel.add(exploreArea02);

        // Split pane to divide left and right sections
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(150);
        splitPane.setResizeWeight(0.0);
        splitPane.setContinuousLayout(true);
        splitPane.setEnabled(false);

        // Add split pane to the central panel
        panel04.add(splitPane, BorderLayout.CENTER);


        footerPanel = new JPanel(new GridLayout(1, 1));
        footerPanel.setPreferredSize(new Dimension(0, 50));
        footerLabel = new JLabel("Footer", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        footerPanel.add(footerLabel);

        mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.add(titleBar, BorderLayout.NORTH);
        mainContainer.add(toolbarPanel);
        mainContainer.add(menubarPanel);
        mainContainer.add(panel04);
        mainContainer.add(commandPanel);
        mainContainer.add(footerPanel);

        frame.add(mainContainer, BorderLayout.CENTER);

    
        frame.setVisible(true);
    }
}

