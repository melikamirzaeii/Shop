# Shopping Cart Application

This Java application provides a simple graphical user interface (GUI) for managing a shopping cart. Users can view products, add them to their cart, and view their profile information. The application uses Swing for the GUI and connects to a database to retrieve product and user information.

## Features

- View Products: Displays a list of products retrieved from a database.
- Add to Cart: Allows users to add products to their shopping cart.
- View Cart: Displays the contents of the shopping cart.
- User Profile: Shows the user's profile information, including whether the user is an admin.

## Installation and Setup

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A database with tables for products and users
- JDBC driver for your database

### Database Setup

Ensure you have a database set up with the following tables:

#### product Table
- id: INT, Primary Key
- title: VARCHAR, Title of the product
- price: VARCHAR, Price of the product

#### users Table
- id: INT, Primary Key
- username: VARCHAR, Username of the user
- address: VARCHAR, Address of the user
- phone: VARCHAR, Phone number of the user
- isadmin: BOOLEAN, Whether the user is an admin

### Configuration

1. Database Connection:
   Update the DatabaseConnection class with the appropriate JDBC URL, username, and password for your database.

   
   import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.SQLException;

   public class DatabaseConnection {
       public static Connection getConnection() throws SQLException {
           String url = "jdbc:mysql://localhost:3306/your_database";
           String user = "your_username";
           String password = "your_password";
           return DriverManager.getConnection(url, user, password);
       }
   }
   

2. Dependencies:
   Ensure you have the necessary JDBC driver for your database in your classpath.
   ### MainFrame Class

- MainFrame Constructor:
  - Sets up the main window with a card layout.
  - Initializes the home, products, cart, and profile panels.
  - Adds buttons and action listeners for navigation.

- getProductInfoFromDatabase Method:
  - Retrieves product information from the database.

- getUserInfoFromDatabase Method:
  - Retrieves user information from the database.

- updateCartPanel Method:
  - Updates the cart panel with the current items in the cart.

### User Interface

- Home Panel:
  - Contains buttons to navigate to the products, cart, and profile panels.

- Products Panel:
  - Displays a list of products with buttons to add them to the cart.

- Cart Panel:
  - Displays the contents of the shopping cart.

- Profile Panel:
  - Displays the user's profile information.

## Customization

- Adding More Products:
  Update the product table in your database with more product entries.

- User Management:
  Update the users table in your database with user entries.

- GUI Customization:
  Modify the Swing components in the MainFrame class to change the appearance of the GUI.

## Troubleshooting

- Database Connection Issues:
  Ensure the database URL, username, and password are correct.
  Check if the JDBC driver is in the classpath.

- GUI Issues:
  Ensure you are using a compatible version of the JDK.

## Contributing

Feel free to fork this repository and make changes. Pull requests are welcome!

