/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managmentresturantsystem.resturantmanagment.backend;

/**
 *
 * @author hp
 */
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders;

    // Constructor
    public OrderManager() {
        orders = new ArrayList<>();
    }
    
    // adding order to system
    public void addOrder(Order order) throws InvalidOrderException {
        if (order == null) {
            throw new InvalidOrderException("invalid order!");
        }
        orders.add(order);
        System.out.println("order added successfully");
    }

    // display all orders
    public List<Order> getAllOrders() {
        return orders;
    }

    // order completing
    public void completeOrder(int orderId) {
        for (Order order : orders) {
            if (order.orderId == orderId) {
                order.completeOrder();
                break;
            }
        }
    }
}