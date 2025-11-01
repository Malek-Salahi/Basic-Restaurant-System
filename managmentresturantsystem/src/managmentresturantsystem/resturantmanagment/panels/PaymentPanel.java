package managmentresturantsystem.resturantmanagment.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PaymentPanel extends JPanel {

    public PaymentPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));

        JLabel paymentLabel = new JLabel("Payment Details", SwingConstants.CENTER);
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 24));
        paymentLabel.setForeground(new Color(39, 174, 96));
        add(paymentLabel, BorderLayout.NORTH);

        JPanel paymentForm = new JPanel();
        paymentForm.setLayout(new BoxLayout(paymentForm, BoxLayout.Y_AXIS));
        paymentForm.setBackground(new Color(245, 245, 245));

        JLabel paymentMethodLabel = new JLabel("Select Payment Method:");
        paymentMethodLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JRadioButton cardPayment = new JRadioButton("Credit Card");
        JRadioButton cashPayment = new JRadioButton("Cash on Delivery");

        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cardPayment);
        paymentGroup.add(cashPayment);

        paymentForm.add(paymentMethodLabel);
        paymentForm.add(cardPayment);
        paymentForm.add(cashPayment);

        add(paymentForm, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton payButton = createButton("Complete Payment", e -> {
            JOptionPane.showMessageDialog(this, "Payment completed!");
        });

        buttonsPanel.add(payButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addActionListener(listener);
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        return button;
    }
}
