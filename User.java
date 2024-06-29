class User {
    private String name;      // Name of the user
    private String address;   // Address of the user
    private String phone;     // Phone number of the user
    private Boolean isadmin;     // Phone number of the user

    // Constructor to initialize the user with name, address, and phone
    public User(String name, String address, String phone,Boolean isadmin) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isadmin = isadmin;
    }

    // Getter method for the user's name
    public String getName() {
        return name;
    }

    // Getter method for the user's role
    public Boolean getAdmin() {
        return isadmin;
    }

    // Getter method for the user's address
    public String getAddress() {
        return address;
    }

    // Getter method for the user's phone number
    public String getPhone() {
        return phone;
    }
}

