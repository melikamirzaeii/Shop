
import javax.swing.*;  // Import necessary classes for GUI components
import javax.swing.text.StyledEditorKit.BoldAction;

import java.awt.*;  // Import necessary classes for GUI layout
import java.awt.event.ActionEvent;  // Import necessary classes for event handling
import java.awt.event.ActionListener;  // Import necessary classes for event handling
import java.sql.Connection;  // Import necessary classes for SQL connections
import java.sql.PreparedStatement;  // Import necessary classes for SQL statements
import java.sql.ResultSet;  // Import necessary classes for handling SQL results
import java.sql.SQLException;  // Import necessary classes for SQL exceptions
import java.util.ArrayList;  // Import necessary classes for list handling
import java.util.HashMap;  // Import necessary classes for hash map handling
import java.util.Map;  // Import necessary classes for map handling
import java.util.List;  // Import necessary classes for list handling

public class MainFrame extends JFrame {
    private CardLayout cardLayout;  // Card layout to manage different panels
    private JPanel mainPanel;  // Main panel to hold all other panels
    private Map<String, Integer> cartItems; // To store the quantity of each product

    private JPanel cartListPanel; // Declare cartListPanel as a member variable

    // Method to get product information from the database
    public static List<Product> getProductInfoFromDatabase() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM product";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String title = resultSet.getString("title");
                        String price = resultSet.getString("price");
                        products.add(new Product(title, price));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "خطا در بازیابی اطلاعات از دیتابیس");
        }
        return products;
    }

    // Method to get user information from the database
    static User getUserInfoFromDatabase(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("username");
                        String address = resultSet.getString("address");
                        String phone = resultSet.getString("PHONE");
                        Boolean isadmin = resultSet.getBoolean("isadmin");
                        return new User(name, address, phone,isadmin);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "خطا در بازیابی اطلاعات از دیتابیس");
        }
        return null;
    }

    // Constructor for the main frame
    public MainFrame(String username) {
        setTitle("Main Frame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Font font = new Font("Tahoma" , Font.BOLD , 16);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        cartItems = new HashMap<>(); // Initialize cart items

        // Create main page
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(3, 1));

        JButton productsButton = new JButton("محصولات");
        JButton cartButton = new JButton("سبد خرید");
        JButton profileButton = new JButton("پروفایل کاربری");
        profileButton.setFont(font);
        cartButton.setFont(font);
        productsButton.setFont(font);
        productsButton.setBackground(Color.decode("#9BD4E4"));
        cartButton.setBackground(Color.decode("#9BD4E4"));
        profileButton.setBackground(Color.decode("#9BD4E4"));

        homePanel.add(productsButton);
        homePanel.add(cartButton);
        homePanel.add(profileButton);

        // Create products page
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new BorderLayout());

        JLabel productsLabel = new JLabel("محصولات", JLabel.CENTER);
        productsPanel.add(productsLabel, BorderLayout.NORTH);

        JPanel productsListPanel = new JPanel();
        productsListPanel.setLayout(new GridLayout(3, 1));

        // Example product
        List<Product> products = getProductInfoFromDatabase();
        for (Product product : products) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new GridLayout(2, 1));
            JLabel productLabel = new JLabel(product.getTitle() + " - قیمت: " + product.getPrice() + " هزار تومان");
            JButton addButton = new JButton("+");
            final String productName = product.getTitle(); // For use in the ActionListener
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cartItems.put(productName, cartItems.getOrDefault(productName, 0) + 1);  // Add product to cart
                }
            });
            productPanel.add(productLabel);
            productPanel.add(addButton);
            productsListPanel.add(productPanel);
            System.out.println("Title: " + product.getTitle() + ", Price: " + product.getPrice());
        }

        productsPanel.add(productsListPanel, BorderLayout.CENTER);

        JButton backFromProductsButton = new JButton("برگشت");
        productsPanel.add(backFromProductsButton, BorderLayout.SOUTH);

        // Create cart page
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());

        JLabel cartLabel = new JLabel("سبد خرید", JLabel.CENTER);
        cartPanel.add(cartLabel, BorderLayout.NORTH);

        cartListPanel = new JPanel(); // Initialize cartListPanel
        cartListPanel.setLayout(new GridLayout(3, 1));

        // Example cart item
        for (int i = 1; i <= 3; i++) {
            JLabel cartItemLabel = new JLabel("محصول " + i + " - تعداد: " + 0);
            cartListPanel.add(cartItemLabel);
        }

        cartPanel.add(cartListPanel, BorderLayout.CENTER);

        JButton backFromCartButton = new JButton("برگشت");
        cartPanel.add(backFromCartButton, BorderLayout.SOUTH);

        // Create profile page
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());

        JLabel profileLabel = new JLabel("پروفایل کاربری", JLabel.CENTER);
        profilePanel.add(profileLabel, BorderLayout.NORTH);

        JButton backFromProfileButton = new JButton("برگشت");
        profilePanel.add(backFromProfileButton, BorderLayout.SOUTH);

        // Panel for displaying user information
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        profilePanel.add(userInfoPanel, BorderLayout.CENTER);

        // Fetch user info from database
        User user = getUserInfoFromDatabase(username);
        if (user != null) {
            JLabel nameLabel = new JLabel("نام کاربر: " + user.getName(), JLabel.CENTER);
            JLabel addressLabel = new JLabel("آدرس: " + user.getAddress(), JLabel.CENTER);
            JLabel phoneLabel = new JLabel("تلفن: " + user.getPhone(), JLabel.CENTER);
            if(user.getAdmin()){
                JLabel isadmin = new JLabel("کاربر ادمین است", JLabel.CENTER);
                userInfoPanel.add(isadmin);
            }else{
                JLabel isadmin = new JLabel("کاربر ادمین نیست", JLabel.CENTER);
                userInfoPanel.add(isadmin);
            }
            userInfoPanel.add(nameLabel);
            userInfoPanel.add(addressLabel);
            userInfoPanel.add(phoneLabel);
        } else {
            JLabel notFoundLabel = new JLabel("کاربر یافت نشد", JLabel.CENTER);
            userInfoPanel.add(notFoundLabel);
        }

        // Add panels to main panel
        mainPanel.add(homePanel, "Home");
        mainPanel.add(productsPanel, "Products");
        mainPanel.add(cartPanel, "Cart");
        mainPanel.add(profilePanel, "Profile");

        add(mainPanel);

        // Add action listeners for navigation buttons
        productsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Products");
            }
        });
        cartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update cart panel before showing it
                updateCartPanel();
                cardLayout.show(mainPanel, "Cart");
            }
        });
        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Profile");
            }
        });

        backFromProductsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home");
            }
        });
        backFromCartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home");
            }
        });
        backFromProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home");
            }
        });

        setVisible(true); // Show the main frame when created
    }

    // Method to update the cart panel with current items
    private void updateCartPanel() {
        cartListPanel.removeAll();  // Clear previous items
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            JLabel cartItemLabel = new JLabel(entry.getKey() + " - تعداد: " + entry.getValue());
            cartListPanel.add(cartItemLabel);
        }
        cartListPanel.revalidate();
        cartListPanel.repaint();
    }


}
