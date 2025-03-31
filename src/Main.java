import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Canadian Tax Calculator application.
 * Calculates tax amounts based on Canadian provinces in Canadian dollars (CAD).
 */
public class Main implements ActionListener {

    private record TaxInfo(double gstRate, double pstRate, String taxType) {}

    // Constants for tax rates
    private static final Map<String, TaxInfo> PROVINCE_TAX_RATES = new LinkedHashMap<>() {{
        put("Alberta", new TaxInfo(0.05, 0.0, "GST Only"));
        put("Northwest Territories", new TaxInfo(0.05, 0.0, "GST Only"));
        put("Nunavut", new TaxInfo(0.05, 0.0, "GST Only"));
        put("Yukon", new TaxInfo(0.05, 0.0, "GST Only"));
        put("British Columbia", new TaxInfo(0.05, 0.07, "GST + PST"));
        put("Manitoba", new TaxInfo(0.05, 0.07, "GST + PST"));
        put("Saskatchewan", new TaxInfo(0.05, 0.06, "GST + PST"));
        put("New Brunswick", new TaxInfo(0.05, 0.10, "HST"));
        put("Newfoundland and Labrador", new TaxInfo(0.05, 0.10, "HST"));
        put("Nova Scotia", new TaxInfo(0.05, 0.10, "HST"));
        put("Ontario", new TaxInfo(0.05, 0.08, "HST"));
        put("Prince Edward Island", new TaxInfo(0.05, 0.10, "HST"));
        put("Quebec", new TaxInfo(0.05, 0.09975, "GST + QST"));
    }};

    // UI Components
    private JButton clearButton, calculateButton;
    private JTextField priceField, gstAmountField, pstAmountField, totalTaxField, resultField;
    private JLabel currentProvinceLabel;
    private JFrame frame;
    private final DecimalFormat df = new DecimalFormat("0.00");

    // Status bar
    private JLabel statusBar;

    // Selected province
    private String selectedProvince = "Alberta";

    public Main() {
        initializeUI();
    }

    /** Initializes the user interface. */
    private void initializeUI() {
        frame = new JFrame("Canadian Tax Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(450, 400));

        // Set application icon
        ImageIcon icon = createImageIcon("/resources/taxicon.png");
        if (icon != null) frame.setIconImage(icon.getImage());

        // Main panel setup
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(mainPanel);

        // Add panels using helper method
        JPanel inputPanel = createPanel(new GridBagLayout(), "Input");
        JPanel resultsPanel = createPanel(new GridBagLayout(), "Tax Breakdown");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resultsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Status bar
        statusBar = new JLabel(" ");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        mainPanel.add(statusBar, BorderLayout.PAGE_END);

        // Setup components in their panels
        setupInputPanel(inputPanel);
        setupResultsPanel(resultsPanel);
        setupButtons(buttonPanel);
        createMenuBar();

        // Add Enter key listener to price field
        priceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) calculateTax();
            }
        });

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /** Creates a panel with a titled border. */
    private JPanel createPanel(LayoutManager layout, String title) {
        JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    /** Sets up the input panel. */
    private void setupInputPanel(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        panel.add(new JLabel("Price (CAD):"), gbc);

        gbc.gridx = 1; gbc.weightx = 0.7;
        priceField = new JTextField(10);
        priceField.setToolTipText("Enter the pre-tax price in CAD");
        panel.add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        currentProvinceLabel = new JLabel("Province: " + selectedProvince);
        panel.add(currentProvinceLabel, gbc);
    }

    /** Sets up the results panel. */
    private void setupResultsPanel(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"GST Amount:", "PST/HST Amount:", "Total Tax:", "Total (with Tax):"};
        JTextField[] fields = {gstAmountField = new JTextField(10), pstAmountField = new JTextField(10),
                totalTaxField = new JTextField(10), resultField = new JTextField(10)};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0.3;
            panel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1; gbc.weightx = 0.7;
            fields[i].setEditable(false);
            if (i == 3) fields[i].setFont(fields[i].getFont().deriveFont(Font.BOLD));
            panel.add(fields[i], gbc);
        }
    }

    /** Creates a configured JButton. */
    private JButton createButton(String text, int mnemonic, String tooltip, ActionListener listener) {
        JButton button = new JButton(text);
        button.setMnemonic(mnemonic);
        button.setToolTipText(tooltip);
        button.addActionListener(listener);
        return button;
    }

    /** Sets up the buttons using the helper method. */
    private void setupButtons(JPanel panel) {
        clearButton = createButton("Clear", KeyEvent.VK_C, "Clear all fields (Alt+C)", this);
        panel.add(clearButton);

        calculateButton = createButton("Calculate", KeyEvent.VK_A, "Calculate tax (Alt+A)", this);
        panel.add(calculateButton);
    }

    /** Creates the menu bar. */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu provinceMenu = new JMenu("Province");
        provinceMenu.setMnemonic(KeyEvent.VK_P);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem aboutItem = new JMenuItem("About", KeyEvent.VK_A);
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        PROVINCE_TAX_RATES.keySet().forEach(p -> {
            JMenuItem item = new JMenuItem(p);
            item.addActionListener(this);
            provinceMenu.add(item);
        });

        menuBar.add(provinceMenu);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
    }

    /** Shows the about dialog. */
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(frame,
                "Canadian Tax Calculator\nA simple utility to calculate tax amounts across Canadian provinces.",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Loads an ImageIcon from a path. */
    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        return imgURL != null ? new ImageIcon(imgURL) : null;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't set system look and feel: " + e.getMessage());
        }
        SwingUtilities.invokeLater(Main::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == clearButton) {
            clearFields();
        } else if (source == calculateButton) {
            calculateTax();
        } else if (source instanceof JMenuItem item) {
            handleMenuSelection(item);
        }
    }

    /** Handles menu selections. */
    private void handleMenuSelection(JMenuItem item) {
        selectedProvince = item.getText();
        TaxInfo taxInfo = PROVINCE_TAX_RATES.get(selectedProvince);
        currentProvinceLabel.setText("Province: " + selectedProvince + " (" + taxInfo.taxType() + ")");
        statusBar.setText("Selected province: " + selectedProvince);
        if (!priceField.getText().trim().isEmpty()) {
            calculateTax();
        }
    }

    /** Clears all fields. */
    private void clearFields() {
        priceField.setText("");
        gstAmountField.setText("");
        pstAmountField.setText("");
        totalTaxField.setText("");
        resultField.setText("");
        statusBar.setText("Fields cleared");
    }

    /** Calculates tax based on input in CAD. */
    private void calculateTax() {
        String priceText = priceField.getText().trim();
        if (priceText.isEmpty()) {
            showError("Please enter a price.");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceText.replace(',', '.'));
            if (price < 0) {
                showError("Price cannot be negative.");
                return;
            }
        } catch (NumberFormatException ex) {
            showError("Invalid price format. Please enter a valid number.");
            return;
        }

        // Calculate taxes directly in CAD
        TaxInfo taxInfo = PROVINCE_TAX_RATES.get(selectedProvince);
        double gstAmount = price * taxInfo.gstRate();
        double pstAmount = price * taxInfo.pstRate();
        double totalTax = gstAmount + pstAmount;
        double total = price + totalTax;

        // Display results with CAD symbol
        gstAmountField.setText("$" + df.format(gstAmount));
        pstAmountField.setText("$" + df.format(pstAmount));
        totalTaxField.setText("$" + df.format(totalTax));
        resultField.setText("$" + df.format(total));
        statusBar.setText("Tax calculated successfully for " + selectedProvince);
    }

    /** Shows an error message. */
    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
        statusBar.setText("Error: " + message);
    }
}