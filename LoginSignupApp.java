package shop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSignupApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame();
            }
        });
    }
}

class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        
        panel.add(new JLabel("نام کاربری:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("رمز عبور:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        JButton loginButton = new JButton("ورود");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals("root") && password.equals("root")) {
                    new MainFrame();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "نام کاربری یا رمز عبور اشتباه است");
                }
            }
        });
        panel.add(loginButton);
        
        JButton signupButton = new JButton("ثبت نام");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignupFrame();
                dispose();
            }
        });
        panel.add(signupButton);
        
        add(panel);
        setVisible(true);
    }
}

class SignupFrame extends JFrame {
    private JTextField addressField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JTextField usernameField;

    public SignupFrame() {
        setTitle("ثبت نام");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        
        panel.add(new JLabel("آدرس:"));
        addressField = new JTextField();
        panel.add(addressField);
        
        panel.add(new JLabel("شماره:"));
        phoneField = new JTextField();
        panel.add(phoneField);
        
        panel.add(new JLabel("نام کاربری:"));
        usernameField = new JTextField();
        panel.add(usernameField);
        
        panel.add(new JLabel("رمز عبور:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);
        
        JButton signupButton = new JButton("ثبت نام");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle signup logic here
                String address = addressField.getText();
                String phone = phoneField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (address.length() < 5) {
                    JOptionPane.showMessageDialog(null, "آدرس باید حداقل ۵ کاراکتر باشد");
                } else if (phone.length() < 5) {
                    JOptionPane.showMessageDialog(null, "شماره باید حداقل ۵ کاراکتر باشد");
                } else if (!phone.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "شماره باید فقط شامل عدد باشد");
                } else if (username.length() < 5) {
                    JOptionPane.showMessageDialog(null, "نام کاربری باید حداقل ۵ کاراکتر باشد");
                } else if (password.length() < 5) {
                    JOptionPane.showMessageDialog(null, "رمز عبور باید حداقل ۵ کاراکتر باشد");
                } else if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
                    JOptionPane.showMessageDialog(null, "رمز عبور باید شامل حروف بزرگ، حروف کوچک و عدد باشد");
                } else {
                    JOptionPane.showMessageDialog(null, "ثبت نام با آدرس: " + address + "، شماره: " + phone + "، نام کاربری: " + username + " و رمز عبور: " + password);
                    new shop.MainFrame();
                    dispose();
                }
            }
        });
        panel.add(signupButton);
        
        JButton loginButton = new JButton("ورود");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame();
                dispose();
            }
        });
        panel.add(loginButton);
        
        add(panel);
        setVisible(true);
    }
}
