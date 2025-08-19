import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CalculatorApp {
        public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Calculator");
        
        JTextField num1 = new JTextField();
        num1.setBounds(50, 40, 150, 30);
        
        JTextField num2 = new JTextField();
        num2.setBounds(50, 80, 150, 30);

        JButton add = new JButton("+");
        add.setBounds(50, 130, 50, 30);

        JButton sub = new JButton("-");
        sub.setBounds(110, 130, 50, 30);

        JButton mul = new JButton("*");
        mul.setBounds(170, 130, 50, 30);

        JButton div = new JButton("/");
        div.setBounds(230, 130, 50, 30);

        JLabel result = new JLabel("Result: ");
        result.setBounds(50, 180, 200, 30);

        frame.add(num1);
        frame.add(num2);
        frame.add(add);
        frame.add(sub);
        frame.add(mul);
        frame.add(div);
        frame.add(result);

        frame.setSize(350, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button logic
        add.addActionListener(e -> {
            double a = Double.parseDouble(num1.getText());
            double b = Double.parseDouble(num2.getText());
            result.setText("Result: " + (a + b));
        });

        sub.addActionListener(e -> {
            double a = Double.parseDouble(num1.getText());
            double b = Double.parseDouble(num2.getText());
            result.setText("Result: " + (a - b));
        });

        mul.addActionListener(e -> {
            double a = Double.parseDouble(num1.getText());
            double b = Double.parseDouble(num2.getText());
            result.setText("Result: " + (a * b));
        });

        div.addActionListener(e -> {
            double a = Double.parseDouble(num1.getText());
            double b = Double.parseDouble(num2.getText());
            if (b != 0)
                result.setText("Result: " + (a / b));
            else
                result.setText("Cannot divide by zero!");
        });
    }
}

