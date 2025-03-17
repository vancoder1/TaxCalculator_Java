import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Main implements ActionListener {
    private double taxRate = 0.13; // Default 13% tax rate (example)
    private String selectedCurrency = "CAD"; // Default currency
    private static final Map<String, Double> PROVINCE_TAX_RATES = new HashMap<>();

    JButton clearButton, calculateButton;
    JTextField priceField, taxableAmountField, resultField;
    JMenuItem cadItem, usdItem, euroItem, gbpItem;
    JMenuItem abItem, bcItem, mbItem, nbItem, nlItem, nsItem, ntItem, nuItem, onItem, peItem, qcItem, skItem, ytItem;
    JFrame frame;

    Main() {
        initializeProvinceTaxRates();
        // Create JFrame
        frame = new JFrame("Tax Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 300));

        //Main Panel setup
        JPanel mainPanel = new JPanel(new GridBagLayout());
        frame.add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; //make it responsive

        // Create JLabels
        JLabel priceLabel = new JLabel("Price:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(priceLabel, gbc);

        JLabel taxableAmountLabel = new JLabel("Taxable Amount:");
        gbc.gridy = 1;
        mainPanel.add(taxableAmountLabel, gbc);

        JLabel resultLabel = new JLabel("Total (with Tax):");
        gbc.gridy = 2;
        mainPanel.add(resultLabel, gbc);

        // Create JTextFields
        priceField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(priceField, gbc);

        taxableAmountField = new JTextField();
        taxableAmountField.setEditable(false);
        gbc.gridy = 1;
        mainPanel.add(taxableAmountField, gbc);

        resultField = new JTextField();
        resultField.setEditable(false);
        gbc.gridy = 2;
        mainPanel.add(resultField, gbc);

        //Create buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        buttonPanel.add(calculateButton);

        // Reset grid-width after buttons
        gbc.gridwidth = 1;

        // Create JMenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu provinceMenu = new JMenu("Province");
        JMenu currencyMenu = new JMenu("Currency");

        // Province Menu Items
        abItem = new JMenuItem("Alberta");
        bcItem = new JMenuItem("British Columbia");
        mbItem = new JMenuItem("Manitoba");
        nbItem = new JMenuItem("New Brunswick");
        nlItem = new JMenuItem("Newfoundland and Labrador");
        nsItem = new JMenuItem("Nova Scotia");
        ntItem = new JMenuItem("Northwest Territories");
        nuItem = new JMenuItem("Nunavut");
        onItem = new JMenuItem("Ontario");
        peItem = new JMenuItem("Prince Edward Island");
        qcItem = new JMenuItem("Quebec");
        skItem = new JMenuItem("Saskatchewan");
        ytItem = new JMenuItem("Yukon");

        abItem.addActionListener(this);
        bcItem.addActionListener(this);
        mbItem.addActionListener(this);
        nbItem.addActionListener(this);
        nlItem.addActionListener(this);
        nsItem.addActionListener(this);
        ntItem.addActionListener(this);
        nuItem.addActionListener(this);
        onItem.addActionListener(this);
        peItem.addActionListener(this);
        qcItem.addActionListener(this);
        skItem.addActionListener(this);
        ytItem.addActionListener(this);

        provinceMenu.add(abItem);
        provinceMenu.add(bcItem);
        provinceMenu.add(mbItem);
        provinceMenu.add(nbItem);
        provinceMenu.add(nlItem);
        provinceMenu.add(nsItem);
        provinceMenu.add(ntItem);
        provinceMenu.add(nuItem);
        provinceMenu.add(onItem);
        provinceMenu.add(peItem);
        provinceMenu.add(qcItem);
        provinceMenu.add(skItem);
        provinceMenu.add(ytItem);

        // Currency Menu Items
        cadItem = new JMenuItem("CAD");
        usdItem = new JMenuItem("USD");
        euroItem = new JMenuItem("EURO");
        gbpItem = new JMenuItem("UK POUNDS");

        cadItem.addActionListener(this);
        usdItem.addActionListener(this);
        euroItem.addActionListener(this);
        gbpItem.addActionListener(this);

        currencyMenu.add(cadItem);
        currencyMenu.add(usdItem);
        currencyMenu.add(euroItem);
        currencyMenu.add(gbpItem);

        menuBar.add(provinceMenu);
        menuBar.add(currencyMenu);
        frame.setJMenuBar(menuBar);

        frame.pack(); // Adjusts frame size to fit components
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            priceField.setText("");
            taxableAmountField.setText("");
            resultField.setText("");
        } else if (e.getSource() == calculateButton) {
            String priceText = priceField.getText();
            if (priceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a price.");
                return;
            }
            double price;
            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid price format.");
                return;
            }
            if (price < 0) {
                JOptionPane.showMessageDialog(frame, "Price cannot be negative.");
                return;
            }

            double taxableAmount = price * taxRate;
            double totalPrice = price + taxableAmount;
            DecimalFormat df = new DecimalFormat("0.00");
            taxableAmountField.setText(df.format(taxableAmount) + " " + selectedCurrency);
            resultField.setText(df.format(totalPrice) + " " + selectedCurrency);
        } else if (e.getSource() == cadItem) {
            selectedCurrency = "CAD";
        } else if (e.getSource() == usdItem) {
            selectedCurrency = "USD";
        } else if (e.getSource() == euroItem) {
            selectedCurrency = "EURO";
        } else if (e.getSource() == gbpItem) {
            selectedCurrency = "GBP";
        } else if (e.getSource() == abItem) {
            taxRate = PROVINCE_TAX_RATES.get("Alberta");
        } else if (e.getSource() == bcItem) {
            taxRate = PROVINCE_TAX_RATES.get("British Columbia");
        } else if (e.getSource() == mbItem) {
            taxRate = PROVINCE_TAX_RATES.get("Manitoba");
        } else if (e.getSource() == nbItem) {
            taxRate = PROVINCE_TAX_RATES.get("New Brunswick");
        } else if (e.getSource() == nlItem) {
            taxRate = PROVINCE_TAX_RATES.get("Newfoundland and Labrador");
        } else if (e.getSource() == nsItem) {
            taxRate = PROVINCE_TAX_RATES.get("Nova Scotia");
        } else if (e.getSource() == ntItem) {
            taxRate = PROVINCE_TAX_RATES.get("Northwest Territories");
        } else if (e.getSource() == nuItem) {
            taxRate = PROVINCE_TAX_RATES.get("Nunavut");
        } else if (e.getSource() == onItem) {
            taxRate = PROVINCE_TAX_RATES.get("Ontario");
        } else if (e.getSource() == peItem) {
            taxRate = PROVINCE_TAX_RATES.get("Prince Edward Island");
        } else if (e.getSource() == qcItem) {
            taxRate = PROVINCE_TAX_RATES.get("Quebec");
        } else if (e.getSource() == skItem) {
            taxRate = PROVINCE_TAX_RATES.get("Saskatchewan");
        } else if (e.getSource() == ytItem) {
            taxRate = PROVINCE_TAX_RATES.get("Yukon");
        }
    }

    private void initializeProvinceTaxRates() {
        PROVINCE_TAX_RATES.put("Alberta", 0.05);
        PROVINCE_TAX_RATES.put("British Columbia", 0.12);
        PROVINCE_TAX_RATES.put("Manitoba", 0.12);
        PROVINCE_TAX_RATES.put("New Brunswick", 0.15);
        PROVINCE_TAX_RATES.put("Newfoundland and Labrador", 0.15);
        PROVINCE_TAX_RATES.put("Nova Scotia", 0.15);
        PROVINCE_TAX_RATES.put("Northwest Territories", 0.05);
        PROVINCE_TAX_RATES.put("Nunavut", 0.05);
        PROVINCE_TAX_RATES.put("Ontario", 0.13);
        PROVINCE_TAX_RATES.put("Prince Edward Island", 0.15);
        PROVINCE_TAX_RATES.put("Quebec", 0.14975);
        PROVINCE_TAX_RATES.put("Saskatchewan", 0.11);
        PROVINCE_TAX_RATES.put("Yukon", 0.05);
    }
}
