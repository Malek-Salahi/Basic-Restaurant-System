package managmentresturantsystem.resturantmanagment.panels;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import managmentresturantsystem.resturantmanagment.ApplicationController;
import managmentresturantsystem.resturantmanagment.backend.*;

public class MenuPanel extends JPanel {
    private ApplicationController controller;
    private ButtonGroup orderTypeGroup;
    private Map<Dish, JCheckBox> dishSelectionMap;
    private Map<Dish, JSpinner> dishQuantityMap;

    public MenuPanel(ApplicationController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));

        // Menu Label
        JLabel menuLabel = new JLabel("Menu", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 28));
        menuLabel.setForeground(new Color(52, 152, 219));
        add(menuLabel, BorderLayout.NORTH);

        // Menu content panel with BoxLayout
        JPanel menuContentPanel = new JPanel();
        menuContentPanel.setLayout(new BoxLayout(menuContentPanel, BoxLayout.Y_AXIS));
        menuContentPanel.setBackground(new Color(245, 245, 245));

        // Load dishes from file
        List<Dish> dishes = loadDishesFromFile();
        dishSelectionMap = new HashMap<>();
        dishQuantityMap = new HashMap<>();

        for (Dish dish : dishes) {
            JPanel itemPanel = new JPanel(new FlowLayout());
            itemPanel.setBackground(new Color(236, 240, 241));
            itemPanel.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));

            // Dish image label
            JLabel imageLabel = new JLabel();
            if (dish.getImagePath() != null && !dish.getImagePath().isEmpty()) {
                File imageFile = new File(dish.getImagePath());
                if (imageFile.exists()) {
                    imageLabel.setIcon(new ImageIcon(new ImageIcon(dish.getImagePath())
                            .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                } else {
                    imageLabel.setText("No Image");
                }
            } else {
                imageLabel.setText("No Image");
            }

            // Dish name and price label
            JLabel itemLabel = new JLabel(dish.getName() + " - $" + dish.getPrice());
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            itemLabel.setForeground(new Color(44, 62, 80));

            // Dish selection checkbox
            JCheckBox dishCheckBox = new JCheckBox();
            dishCheckBox.setBackground(new Color(245, 245, 245));
            dishSelectionMap.put(dish, dishCheckBox);

            // Quantity spinner
            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
            dishQuantityMap.put(dish, quantitySpinner);

            // Add components to the item panel
            itemPanel.add(dishCheckBox);
            itemPanel.add(imageLabel);
            itemPanel.add(itemLabel);
            itemPanel.add(quantitySpinner);

            // Add item panel to the menu content panel
            menuContentPanel.add(itemPanel);
        }

        // JScrollPane to enable scrolling
        JScrollPane scrollPane = new JScrollPane(menuContentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Add scroll pane to the main panel
        add(scrollPane, BorderLayout.CENTER);

        // Order type panel
        JPanel orderTypePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        orderTypePanel.setBackground(new Color(245, 245, 245));

        JCheckBox dineInCheckBox = new JCheckBox("Dine In");
        dineInCheckBox.setBackground(new Color(245, 245, 245));
        dineInCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));

        JCheckBox deliveryCheckBox = new JCheckBox("Delivery");
        deliveryCheckBox.setBackground(new Color(245, 245, 245));
        deliveryCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));

        JCheckBox takeAwayCheckBox = new JCheckBox("Take Away");
        takeAwayCheckBox.setBackground(new Color(245, 245, 245));
        takeAwayCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));

        // Group the checkboxes to allow only one selection
        orderTypeGroup = new ButtonGroup();
        orderTypeGroup.add(dineInCheckBox);
        orderTypeGroup.add(deliveryCheckBox);
        orderTypeGroup.add(takeAwayCheckBox);

        orderTypePanel.add(dineInCheckBox);
        orderTypePanel.add(deliveryCheckBox);
        orderTypePanel.add(takeAwayCheckBox);

        // Add the order type panel to the bottom
        add(orderTypePanel, BorderLayout.SOUTH);

        // Save Order Button
        JButton saveOrderButton = new JButton("Save Order");
        saveOrderButton.addActionListener(e -> {
            try {
                saveOrderToFile();
                showOrderStatus();
            } catch (InvalidOrderException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid Order", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving order: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        orderTypePanel.add(saveOrderButton);
    }

    // Load dishes from file
    private List<Dish> loadDishesFromFile() {
        List<Dish> dishes = new ArrayList<>();
        File file = new File("D:\\managmentresturantsystem\\src\\managmentresturantsystem\\resturantmanagment\\backend\\menu_items.txt");  // Path to your menu file
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        dishes.add(Dish.fromFileString(line.trim()));
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line + " - " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading menu items: " + e.getMessage());
            }
        }
        return dishes;
    }

    private void saveOrderToFile() throws InvalidOrderException, IOException {
        String selectedOrderType = getSelectedOrderType();
        if (selectedOrderType == null) {
            throw new InvalidOrderException("Please select an order type.");
        }

        Order order = new Order();
        for (Map.Entry<Dish, JCheckBox> entry : dishSelectionMap.entrySet()) {
            if (entry.getValue().isSelected()) {
                int quantity = (int) dishQuantityMap.get(entry.getKey()).getValue();
                order.addDish(entry.getKey(), quantity);
            }
        }

        if (order.getTotalPrice() == 0) {
            throw new InvalidOrderException("Please select at least one dish.");
        }

        order.setOrderType(selectedOrderType);

        File file;
        file = new File("D:\\managmentresturantsystem\\src\\managmentresturantsystem\\resturantmanagment\\backend\\saved_orders.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write("Order Type: " + order.getOrderType() + "\n");
            writer.write(order.getOrderDetails() + "\n\n");
        }
    }

    private String getSelectedOrderType() {
        for (AbstractButton button : java.util.Collections.list(orderTypeGroup.getElements())) {
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
private void showOrderStatus() {
    JFrame statusFrame = new JFrame("Order Status");
    statusFrame.setLayout(new BorderLayout());

    JLabel statusLabel = new JLabel("Your order is under preparation...", SwingConstants.CENTER);
    statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
    statusFrame.add(statusLabel, BorderLayout.CENTER);

    JButton paymentButton = new JButton("Go to Payment");
    paymentButton.setFont(new Font("Arial", Font.BOLD, 14));
    paymentButton.setVisible(false); // Initially hidden
    paymentButton.addActionListener(e -> {
        statusFrame.dispose(); // Close the status frame
        controller.showPanel("Payment"); // Navigate to the PaymentPanel
    });
    statusFrame.add(paymentButton, BorderLayout.SOUTH);

    statusFrame.setSize(300, 150);
    statusFrame.setLocationRelativeTo(this);
    statusFrame.setVisible(true);

    // Create a new thread to update the status and show the button after 3 seconds
    new Thread(() -> {
        try {
            Thread.sleep(3000);
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Your order is on the way!");
                paymentButton.setVisible(true); // Make the button visible
            });
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }).start();
}

}
