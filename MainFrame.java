
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, Integer> cartItems; // To store the quantity of each product
    private JPanel cartListPanel; // Declare cartListPanel as a member variable

    public MainFrame() {
        setTitle("Main Frame");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        cartItems = new HashMap<>(); // Initialize cart items

        // Create main page
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(3, 1));

        JButton productsButton = new JButton("محصولات");
        JButton cartButton = new JButton("سبد خرید");
        JButton profileButton = new JButton("پروفایل کاربری");

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
        for (int i = 1; i <= 3; i++) {
            JPanel productPanel = new JPanel();
            productPanel.setLayout(new GridLayout(2, 1));
            JLabel productLabel = new JLabel("محصول " + i + " - قیمت: 50 هزار تومان");
            JButton addButton = new JButton("+");
            final int productId = i; // For use in the ActionListener
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String productName = "محصول " + productId;
                    cartItems.put(productName, cartItems.getOrDefault(productName, 0) + 1);
                }
            });
            productPanel.add(productLabel);
            productPanel.add(addButton);
            productsListPanel.add(productPanel);
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

        JLabel userInfoLabel = new JLabel("نام کاربر: نمونه کاربر", JLabel.CENTER);
        profilePanel.add(userInfoLabel, BorderLayout.CENTER);

        JButton backFromProfileButton = new JButton("برگشت");
        profilePanel.add(backFromProfileButton, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(homePanel, "Home");
        mainPanel.add(productsPanel, "Products");
        mainPanel.add(cartPanel, "Cart");
        mainPanel.add(profilePanel, "Profile");

        add(mainPanel);

        // Add action listeners
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

    private void updateCartPanel() {
        cartListPanel.removeAll();
        for (Map.Entry<String, Integer> entry : cartItems.entrySet()) {
            JLabel cartItemLabel = new JLabel(entry.getKey() + " - تعداد: " + entry.getValue());
            cartListPanel.add(cartItemLabel);
        }
        cartListPanel.revalidate();
        cartListPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}
