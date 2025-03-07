import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    private static final double TAX_RATE = 0.13; // 13% tax rate (example)
    JButton clearButton, calculateButton;
    JTextField priceField, taxableAmountField, resultField;

    Main() {
        // Create JFrame
        JFrame frame = new JFrame("Tax Calculator");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create JLabels
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(20, 20, 100, 20);
        JLabel taxableAmountLabel = new JLabel("Taxable Amount:");
        taxableAmountLabel.setBounds(20, 50, 100, 20);
        JLabel resultLabel = new JLabel("Total (with Tax):");
        resultLabel.setBounds(20, 80, 100, 20);

        // Create JTextFields
        priceField = new JTextField();
        priceField.setBounds(130, 20, 150, 20);
        taxableAmountField = new JTextField();
        taxableAmountField.setBounds(130, 50, 150, 20);
        taxableAmountField.setEditable(false);
        resultField = new JTextField();
        resultField.setBounds(130, 80, 150, 20);
        resultField.setEditable(false);

        // Create JButtons
        clearButton = new JButton("Clear");
        clearButton.setBounds(80, 150, 80, 30);
        clearButton.addActionListener(this);

        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(180, 150, 100, 30);
        calculateButton.addActionListener(this);

        // Add components to the frame
        frame.add(priceLabel);
        frame.add(taxableAmountLabel);
        frame.add(resultLabel);
        frame.add(priceField);
        frame.add(taxableAmountField);
        frame.add(resultField);
        frame.add(clearButton);
        frame.add(calculateButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            priceField.setText("");
            taxableAmountField.setText("");
            resultField.setText("");
        } else if (e.getSource() == calculateButton) {
            try {
                double price = Double.parseDouble(priceField.getText());
                if (price < 0) {
                    JOptionPane.showMessageDialog(null, "Price cannot be negative.");
                    return;
                }
                double taxableAmount = price * TAX_RATE;
                double totalPrice = price + taxableAmount;

                taxableAmountField.setText(String.format("%.2f", taxableAmount));
                resultField.setText(String.format("%.2f", totalPrice));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the price.");
                priceField.setText("");
                taxableAmountField.setText("");
                resultField.setText("");
            }
        }
    }
}