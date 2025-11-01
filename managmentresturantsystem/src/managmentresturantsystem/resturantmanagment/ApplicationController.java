package managmentresturantsystem.resturantmanagment;

import javax.swing.*;
import java.awt.*;
import java.util.List;         // Import the List interface
import java.util.ArrayList;    // Import the ArrayList class

import managmentresturantsystem.resturantmanagment.backend.Dish;
import managmentresturantsystem.resturantmanagment.backend.Order;


public class ApplicationController {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private Order currentOrder;
    private final List<Order> orderHistory;

    public ApplicationController(JPanel mainPanel) {
        this.cardLayout = (CardLayout) mainPanel.getLayout();
        this.mainPanel = mainPanel;
        this.currentOrder = new Order();
                this.currentOrder = new Order();
        this.orderHistory = new ArrayList<>(); 
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public void registerPanel(String name, JPanel panel) {
        mainPanel.add(panel, name);
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void updateDishQuantity(Dish dish, int quantity) {
        if (quantity > 0) {
            currentOrder.addDish(dish, quantity);
        } else {
            currentOrder.removeDish(dish);
        }
    }
    public JPanel getPanel(String panelName) {
    for (Component component : mainPanel.getComponents()) {
        if (component instanceof JPanel && ((JPanel) component).getName().equals(panelName)) {
            return (JPanel) component;
        }
    }
    return null; // Return null if the panel is not found
}
    public List<Order> getOrderHistory() {
        return orderHistory;
    }
       public void addOrderToHistory(Order order) {
        orderHistory.add(order); // Add completed order to history
    }

}