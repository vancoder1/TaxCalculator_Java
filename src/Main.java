import javax.swing.*;
import java.awt.event.*;

public class Main implements ActionListener {
    JTextField lengthField, widthField, resultAreaField, resultPerimeterField;
    JButton clearButton, calculateButton;
    Main() {
        // Create JFrame
        JFrame frame = new JFrame("Tax Calculator");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create JLabels
        JLabel lengthLabel = new JLabel("Length:");
        lengthLabel.setBounds(20, 20, 60, 20);
        JLabel widthLabel = new JLabel("Width:");
        widthLabel.setBounds(20, 50, 60, 20);
        JLabel areaLabel = new JLabel("Area:");
        areaLabel.setBounds(20, 80, 60, 20);
        JLabel perimeterLabel = new JLabel("Perimeter:");
        perimeterLabel.setBounds(20, 110, 80, 20);

        // Create JTextFields
        lengthField = new JTextField();
        lengthField.setBounds(100, 20, 150, 20);
        widthField = new JTextField();
        widthField.setBounds(100, 50, 150, 20);
        resultAreaField = new JTextField();
        resultAreaField.setBounds(100, 80, 150, 20);
        resultAreaField.setEditable(false);
        resultPerimeterField = new JTextField();
        resultPerimeterField.setBounds(100, 110, 150, 20);
        resultPerimeterField.setEditable(false);

        // Create JButtons
        clearButton = new JButton("Area");
        clearButton.setBounds(100, 150, 80, 30);
        clearButton.addActionListener(this);

        calculateButton = new JButton("Perimeter");
        calculateButton.setBounds(190, 150, 100, 30);
        calculateButton.addActionListener(this);

        // Add components to the frame
        frame.add(lengthLabel);
        frame.add(widthLabel);
        frame.add(areaLabel);
        frame.add(perimeterLabel);
        frame.add(lengthField);
        frame.add(widthField);
        frame.add(resultAreaField);
        frame.add(resultPerimeterField);
        frame.add(clearButton);
        frame.add(calculateButton);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            double length = Double.parseDouble(lengthField.getText());
            double width = Double.parseDouble(widthField.getText());
            if (length == width) {
                JOptionPane.showMessageDialog(null, "It's a square!");
            }
            if (e.getSource() == clearButton) {

                double area = length * width;
                resultAreaField.setText(String.format("%.2f", area));
            } else if (e.getSource() == calculateButton) {
                double perimeter = 2 * (length + width);
                resultPerimeterField.setText(String.format("%.2f", perimeter));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numbers for length and width.");
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}