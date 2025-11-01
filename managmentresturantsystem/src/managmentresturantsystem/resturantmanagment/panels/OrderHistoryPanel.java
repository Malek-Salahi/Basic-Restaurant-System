package managmentresturantsystem.resturantmanagment.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import managmentresturantsystem.resturantmanagment.ApplicationController;
import managmentresturantsystem.resturantmanagment.backend.Order;

public class OrderHistoryPanel extends JPanel {

    private JTextArea historyArea;

    public OrderHistoryPanel(ApplicationController controller) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Order History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.RED);
        add(titleLabel, BorderLayout.NORTH);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyArea.setBackground(new Color(236, 240, 241));
        historyArea.setForeground(new Color(44, 62, 80));

        // Load order history from the controller and display it
        displayOrderHistory(controller.getOrderHistory());

        JScrollPane scrollPane = new JScrollPane(historyArea);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons for saving the order history to a file
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton saveButton = createButton("Save Order History", e -> saveOrderHistoryToFile(controller));
        buttonsPanel.add(saveButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    // Display order history in the JTextArea
    private void displayOrderHistory(List<Order> orderHistory) {
        StringBuilder historyText = new StringBuilder();
        for (int i = 0; i < orderHistory.size(); i++) {
            Order order = orderHistory.get(i);
            historyText.append("Order ").append(i + 1).append(": ").append(order.getOrderDetails()).append("\n\n");
        }
        historyArea.setText(historyText.toString());
    }

    // Save the order history to a text file
    private void saveOrderHistoryToFile(ApplicationController controller) {
        List<Order> orderHistory = controller.getOrderHistory();
        File file = new File("orderHistory.txt");

        try (PrintWriter writer = new PrintWriter(file)) {
            for (int i = 0; i < orderHistory.size(); i++) {
                Order order = orderHistory.get(i);
                writer.println("Order " + (i + 1) + ":");
                writer.println(order.getOrderDetails());
                writer.println("--------------------------------------------------");
            }
            JOptionPane.showMessageDialog(this, "Order history saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error saving order history.");
        }
    }

    // Create buttons with default style
    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(231, 76, 60));
        button.setForeground(Color.WHITE);
        button.addActionListener(listener);
        return button;
    }

    // Overloaded method to create buttons without listeners
    private JButton createButton(String text) {
        return createButton(text, null);
    }
}
