/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managmentresturantsystem.resturantmanagment.panels;
import javax.swing.*;
import java.awt.*;
import managmentresturantsystem.resturantmanagment.ApplicationController;
/**
 *
 * @author hp
 */
public class EmployeeDashboardPanel  extends JPanel {
    public EmployeeDashboardPanel(ApplicationController controller) {
        setLayout(new BorderLayout());

        // Title label for the dashboard
        JLabel titleLabel = new JLabel("Employee Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(46, 204, 113));
        add(titleLabel, BorderLayout.NORTH);

        // Create a JTabbedPane to switch between AddItem and OrderHistory
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add "Add Item" panel
        AddItemPanel addItemPanel = new AddItemPanel(controller);
        tabbedPane.addTab("Add Item", addItemPanel);

        // Add "Order History" panel
        OrderHistoryPanel orderHistoryPanel = new OrderHistoryPanel(controller);
        tabbedPane.addTab("Order History", orderHistoryPanel);

        // Add the tabbed pane to the main panel
        add(tabbedPane, BorderLayout.CENTER);
    }

    public EmployeeDashboardPanel() {
    }
}
