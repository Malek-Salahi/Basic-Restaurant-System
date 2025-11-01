package managmentresturantsystem.resturantmanagment.panels;

import javax.swing.*;
import java.awt.*;
import managmentresturantsystem.resturantmanagment.ApplicationController;
import managmentresturantsystem.resturantmanagment.backend.User;
import managmentresturantsystem.resturantmanagment.backend.UserManager;

public class LoginPanel extends JPanel {
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final UserManager userManager;
    private final JCheckBox employeeCheckBox;
    private final JCheckBox customerCheckBox;

    public LoginPanel(ApplicationController controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize the UserManager to handle users and authentication
        userManager = new UserManager();  // UserManager loads users from file

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Role selection checkboxes
        customerCheckBox = new JCheckBox("Customer");
        employeeCheckBox = new JCheckBox("Employee");
        
        // Ensure only one role can be selected at a time
        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(customerCheckBox);
        roleGroup.add(employeeCheckBox);

        // Add checkboxes to the UI
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.add(customerCheckBox);
        checkBoxPanel.add(employeeCheckBox);
        add(checkBoxPanel, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        loginButton.addActionListener(e -> handleLogin(controller));
        add(loginButton, gbc);

        // Reset button
        JButton resetButton = new JButton("Reset");
        gbc.gridy = 5;
        resetButton.addActionListener(e -> resetFields());
        add(resetButton, gbc);
    }

    private void handleLogin(ApplicationController controller) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Authenticate the user using UserManager
        String message = userManager.login(username, password); // Get the login result message

        if (message.startsWith("Login successful")) { // Check for successful login
            // Get the user from the UserManager to check their role
            User user = userManager.getUserByUsername(username);

            // Check if the correct role is selected
            if (customerCheckBox.isSelected() && "customer".equals(user.getRole())) {
                controller.showPanel("Menu"); // Corrected capitalization here
            } else if (employeeCheckBox.isSelected() && "employee".equals(user.getRole())) {
                controller.showPanel("EmployeeDashboard"); // Corrected capitalization here
            } else {
                // If the role selected does not match the user's role
                showMessageForLimitedTime("Selected role does not match the user's role.");
            }
        } else {
            // If the login failed, display the error message
            showMessageForLimitedTime(message);
        }
    }

    private void showMessageForLimitedTime(String message) {
        // Display the message in a JOptionPane
        JOptionPane pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = pane.createDialog(this, "Login Error");

        // Make the dialog visible
        dialog.setVisible(true);

        // Create a thread to close the message dialog after 5 seconds (5000 milliseconds)
        new Thread(() -> {
            try {
                // Wait for 5 seconds
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Close the dialog safely from the event dispatch thread
            SwingUtilities.invokeLater(() -> dialog.setVisible(false));
        }).start();
    }

    private void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
        customerCheckBox.setSelected(false);
        employeeCheckBox.setSelected(false);
    }
}
