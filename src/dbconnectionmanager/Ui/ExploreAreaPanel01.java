/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnectionmanager.Ui;

import dbconnectionmanager.db.DatabaseConnectorInterface;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.HashMap;

/**
 *
 * @author dasun
 */
public class ExploreAreaPanel01 extends JPanel {

    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private HashMap<DefaultMutableTreeNode, DatabaseConnectorInterface> connectorMap;
    private RightPanel rightPanel;

    public ExploreAreaPanel01(RightPanel rightPanel) {
        this.rightPanel = rightPanel; 
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Database Tree"));

        root = new DefaultMutableTreeNode("Databases");
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        connectorMap = new HashMap<>(); 

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null) {
                return;
            }

            if (selectedNode.isLeaf()) {
                String tableName = selectedNode.getUserObject().toString();
                DefaultMutableTreeNode databaseNode = (DefaultMutableTreeNode) selectedNode.getParent();
                if (databaseNode != null) {
                    DatabaseConnectorInterface connector = connectorMap.get(databaseNode);
                    if (connector != null) {
                        SwingUtilities.invokeLater(() -> rightPanel.selectDataFromTable(connector, tableName));
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);
    }

    
    public void addDatabase(String serverType, String dbName, String[] tables, DatabaseConnectorInterface connector) {
        DefaultMutableTreeNode serverNode = getServerNode(serverType);

        DefaultMutableTreeNode dbNode = new DefaultMutableTreeNode(dbName);

        // Store the connector with the database node
        connectorMap.put(dbNode, connector);

        for (String table : tables) {
            dbNode.add(new DefaultMutableTreeNode(table));
        }

        serverNode.add(dbNode);
        treeModel.reload(root);
    }

    private DefaultMutableTreeNode getServerNode(String serverType) {
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) root.getChildAt(i);
            if (child.getUserObject().equals(serverType)) {
                return child;
            }
        }

        DefaultMutableTreeNode serverNode = new DefaultMutableTreeNode(serverType);
        root.add(serverNode);
        return serverNode;
    }
    
}
