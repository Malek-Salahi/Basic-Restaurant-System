package managmentresturantsystem.resturantmanagment.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private Map<Dish, Integer> dishes;
    int orderId;
private String orderType;
    public Order() {
        dishes = new HashMap<>();
    }

    public void addDish(Dish dish, int quantity) {
        dishes.put(dish, quantity); // Add or update the dish with the given quantity
    }

    public void removeDish(Dish dish) {
        dishes.remove(dish); // Remove dish from the order
    }

    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue(); // Calculate total price
        }
        return total;
    }

    public String getOrderDetails() {
        StringBuilder details = new StringBuilder("Order Details:\n");
        for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
            details.append(entry.getKey().getName())
                   .append(" x ")
                   .append(entry.getValue())
                   .append(" = $")
                   .append(entry.getKey().getPrice() * entry.getValue())
                   .append("\n");
        }
        details.append("\nTotal: $").append(getTotalPrice());
        return details.toString();
    }

    void completeOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Dish> getDishes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setOrderType(String type) {
        this.orderType = type;
    }

    public String getOrderType() {
        return orderType;
    }



}
