public class Product {
    private String title;  // Title of the product
    private String price;  // Price of the product

    // Constructor to initialize the product with a title and price
    public Product(String title, String price) {
        this.title = title;
        this.price = price;
    }

    // Getter method for the product title
    public String getTitle() {
        return title;
    }

    // Getter method for the product price
    public String getPrice() {
        return price;
    }
}

