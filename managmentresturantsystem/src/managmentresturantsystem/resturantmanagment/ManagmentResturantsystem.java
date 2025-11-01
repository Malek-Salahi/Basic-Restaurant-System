package managmentresturantsystem.resturantmanagment;

import javax.swing.*;
import java.awt.CardLayout;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import managmentresturantsystem.resturantmanagment.panels.*;

public class ManagmentResturantsystem extends JFrame {

    public ManagmentResturantsystem() {
        setTitle("Restaurant Management System");
        setSize(750, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the main panel with CardLayout
        JPanel mainPanel = new JPanel(new CardLayout());

        // Create an ApplicationController instance and pass the mainPanel to it
        ApplicationController controller = new ApplicationController(mainPanel);

        // Register panels (UI only, no logic)
        mainPanel.add(new LoginPanel(controller), "Login");
        mainPanel.add(new MenuPanel(controller), "Menu");
        mainPanel.add(new OrderHistoryPanel(controller), "OrderHistory");
        mainPanel.add(new AddItemPanel(controller), "AddItem");
        mainPanel.add(new EmployeeDashboardPanel(controller), "EmployeeDashboard");
        mainPanel.add(new PaymentPanel(), "Payment");

        // Add main panel to frame
        add(mainPanel);

        // Show login panel initially
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "Login");

        // Make the frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Nimbus Look-and-Feel not available.");
        }

        // Initialize the main frame
        new ManagmentResturantsystem();
    }
}
