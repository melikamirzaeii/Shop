import javax.swing.*;  // Import necessary classes for GUI components
import java.awt.*;  // Import necessary classes for GUI layout
import java.awt.event.ActionEvent;  // Import necessary classes for event handling
import java.awt.event.ActionListener;  // Import necessary classes for event handling
import java.sql.Connection;  // Import necessary classes for SQL connections
import java.sql.PreparedStatement;  // Import necessary classes for SQL statements
import java.sql.ResultSet;  // Import necessary classes for handling SQL results
import java.sql.SQLException;  // Import necessary classes for SQL exceptions
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class LoginSignupApp {
    public static void main(String[] args) {
        // Run the LoginFrame on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame();
            }
        });
    }
}

class LoginFrame extends JFrame {
    private JTextField usernameField;  // Field for entering the username
    private JPasswordField passwordField;  // Field for entering the password

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to check if the entered credentials are valid
    public boolean checkCredentials(String username, String password) {
        boolean isValid = false;

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, hashPassword(password));

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        isValid = true;  // If a record is found, credentials are valid
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "خطا در اتصال به دیتابیس");
        }

        return isValid;
    }

    // Constructor for the login frame
    public LoginFrame() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Font font = new Font("Tahoma" , Font.BOLD , 16);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        JLabel label = new JLabel("نام کاربری");
        panel.add(label);  // Label for username field
        label.setFont(font);
        usernameField = new JTextField();  // Text field for username input
        panel.add(usernameField);
        JLabel paslabel = new JLabel("رمز عبور");
        panel.add(paslabel);  // Label for password field
        paslabel.setFont(font);
        passwordField = new JPasswordField();  // Password field for password input
        panel.add(passwordField);

        JButton loginButton = new JButton("ورود");  // Button for login action
        loginButton.setFont(font);
        loginButton.setBackground(Color.decode("#9BD4E4"));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (checkCredentials(username, password)) {
                    new MainFrame(username);  // If credentials are valid, open main frame
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "نام کاربری یا رمز عبور اشتباه است");
                }
            }
        });
        panel.add(loginButton);

        JButton signupButton = new JButton("ثبت نام");  // Button for signup action
        signupButton.setFont(font);
        signupButton.setBackground(Color.decode("#9BD4E4"));
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignupFrame();  // Open signup frame
                dispose();
            }
        });
        panel.add(signupButton);

        add(panel);
        setVisible(true);
    }
}

class SignupFrame extends JFrame {
    private JTextField addressField;  // Field for entering the address
    private JTextField phoneField;  // Field for entering the phone number
    private JPasswordField passwordField;  // Field for entering the password
    private JTextField usernameField;  // Field for entering the username

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to save user information to the database
    private boolean saveUserToDatabase(String address, String phone, String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (address, phone, username, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, address);
                statement.setString(2, phone);
                statement.setString(3, username);
                statement.setString(4, hashPassword(password));
                statement.executeUpdate();

                return true;  // Return true if user is successfully saved
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "خطا در ذخیره‌سازی اطلاعات در دیتابیس");

            return false;  // Return false if there is an error
        }
    }

    // Constructor for the signup frame
    public SignupFrame() {
        Font font = new Font("Tahoma" , Font.BOLD , 16);
        setTitle("ثبت نام");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel address = new JLabel("آدرس");
        address.setFont(font);
        panel.add(address);  // Label for address field
        addressField = new JTextField();  // Text field for address input
        panel.add(addressField);

        JLabel phone = new JLabel("شماره");
        phone.setFont(font);
        panel.add(phone);  // Label for phone number field
        phoneField = new JTextField();  // Text field for phone number input
        panel.add(phoneField);

        JLabel user1 = new JLabel("نام کاربری");
        user1.setFont(font);
        panel.add(user1);  // Label for username field
        usernameField = new JTextField();  // Text field for username input
        panel.add(usernameField);

        JLabel pass = new JLabel("رمز عبور");
        pass.setFont(font);
        panel.add(pass);  // Label for password field
        passwordField = new JPasswordField();  // Password field for password input
        panel.add(passwordField);

        JButton signupButton = new JButton("ثبت نام");  // Button for signup action
        signupButton.setFont(font);
        signupButton.setBackground(Color.decode("#9BD4E4"));
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
                } else if (!phone.matches("^09\\d{9}$")) {
                    JOptionPane.showMessageDialog(null, "شماره باید شامل 09 و 11 رقم عددی باشد");
                } else if (username.length() < 5) {
                    JOptionPane.showMessageDialog(null, "نام کاربری باید حداقل ۵ کاراکتر باشد");
                } else if (password.length() < 5) {
                    JOptionPane.showMessageDialog(null, "رمز عبور باید حداقل ۵ کاراکتر باشد");
                } else if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
                    JOptionPane.showMessageDialog(null, "رمز عبور باید شامل حروف بزرگ، حروف کوچک و عدد باشد");
                } else {
                    if (saveUserToDatabase(address, phone, username, password)) {
                        JOptionPane.showMessageDialog(null, "ثبت نام با آدرس: " + address + "، شماره: " + phone + "، نام کاربری: " + username );
                        new MainFrame(username);  // Open main frame after successful signup
                        dispose();
                    }
                }
            }
        });
        panel.add(signupButton);

        JButton loginButton = new JButton("ورود");  // Button for returning to login frame
        loginButton.setFont(font);
        loginButton.setBackground(Color.decode("#9BD4E4"));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame();  // Open login frame
                dispose();
            }
        });
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }
}

