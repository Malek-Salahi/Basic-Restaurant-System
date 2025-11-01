package managmentresturantsystem.resturantmanagment.backend;

public class Dish {
    private String name;
    private double price;
    private String description;
    private int quantity;
    private String imagePath;

    // Constructor with imagePath
    public Dish(String name, double price, String description, int quantity, String imagePath) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    // Convert a Dish object to a string for file storage
    public String toFileString() {
        return name + "," + price + "," + description + "," + quantity + "," + imagePath;
    }

    // Create a Dish object from a string loaded from a file
public static Dish fromFileString(String fileString) {
    String[] parts = fileString.split(",", 5); // Split into 5 parts
    if (parts.length < 5) {
        throw new IllegalArgumentException("Invalid file format, expected 5 parts: " + fileString);
    }
    try {
        String name = parts[0];
        double price = Double.parseDouble(parts[1]);
        String description = parts[2];
        int quantity = Integer.parseInt(parts[3]);
        String imagePath = parts[4];
        return new Dish(name, price, description, quantity, imagePath);
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Number parsing error: " + fileString + " - " + e.getMessage());
    }
}

}
