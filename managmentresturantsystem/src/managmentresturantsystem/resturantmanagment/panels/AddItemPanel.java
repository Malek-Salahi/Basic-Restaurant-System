package managmentresturantsystem.resturantmanagment.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import managmentresturantsystem.resturantmanagment.backend.Dish;
import managmentresturantsystem.resturantmanagment.ApplicationController;

public class AddItemPanel extends JPanel {
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JTextField itemDescriptionField;
    private JTextField itemQuantityField;
    private JLabel imageLabel;
    private String imagePath;
    private ApplicationController controller;

    public AddItemPanel(ApplicationController controller) {
        this.controller = controller;

        setLayout(new GridLayout(6, 2, 10, 10));

        itemNameField = new JTextField();
        itemPriceField = new JTextField();
        itemDescriptionField = new JTextField();
        itemQuantityField = new JTextField();
        JButton selectImageButton = new JButton("Select Image");
        JButton saveItemButton = new JButton("Save Item");

        imageLabel = new JLabel("No image selected", JLabel.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        add(new JLabel("Item Name:"));
        add(itemNameField);

        add(new JLabel("Item Price:"));
        add(itemPriceField);

        add(new JLabel("Item Description:"));
        add(itemDescriptionField);

        add(new JLabel("Item Quantity:"));
        add(itemQuantityField);

        add(new JLabel("Item Image:"));
        add(selectImageButton);

        add(imageLabel);

        add(saveItemButton);

        // Save item to file and list
        saveItemButton.addActionListener(e -> {
            String itemName = itemNameField.getText().trim();
            String itemPrice = itemPriceField.getText().trim();
            String itemDescription = itemDescriptionField.getText().trim();
            String itemQuantity = itemQuantityField.getText().trim();

            if (!itemName.isEmpty() && !itemPrice.isEmpty() && !itemDescription.isEmpty() && !itemQuantity.isEmpty() && imagePath != null) {
                try {
                    double price = Double.parseDouble(itemPrice);
                    int quantity = Integer.parseInt(itemQuantity);
                    Dish newDish = new Dish(itemName, price, itemDescription, quantity, imagePath);

                    saveDishToFile(newDish);

                    JOptionPane.showMessageDialog(AddItemPanel.this, 
                        "Item saved: " + itemName);

                    resetFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AddItemPanel.this, "Invalid price or quantity. Please enter valid numbers.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(AddItemPanel.this, "Error saving item to file: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(AddItemPanel.this, "Please fill in all fields and select an image.");
            }
        });

        selectImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(AddItemPanel.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePath = selectedFile.getAbsolutePath();
                imageLabel.setText("Image selected: " + selectedFile.getName());
                imageLabel.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            }
        });
    }

    private void saveDishToFile(Dish dish) throws IOException {
        File file = new File("E:\\menu_items.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(dish.toFileString());
            writer.newLine();
        }
    }

    private void resetFields() {
        itemNameField.setText("");
        itemPriceField.setText("");
        itemDescriptionField.setText("");
        itemQuantityField.setText("");
        imageLabel.setText("No image selected");
        imageLabel.setIcon(null);
        imagePath = null;
    }
}
