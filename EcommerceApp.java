import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class Category {
    private String name;
    private List<Product> products;

    public Category(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}

class ShoppingCart {
    private List<Product> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product) {
        items.add(product);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public List<Product> getItems() {
        return items;
    }

    public double getTotalCost() {
        double totalCost = 0;
        for (Product item : items) {
            totalCost += item.getPrice();
        }
        return totalCost;
    }
}

class Customer {
    private String name;
    private String address;
    private ShoppingCart cart;

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
        this.cart = new ShoppingCart();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ShoppingCart getCart() {
        return cart;
    }
}

class ECommerceApplication {
    private List<Category> categories;
    private Customer customer;

    public ECommerceApplication() {
        this.categories = new ArrayList<>();
        this.customer = null;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void displayCategories() {
        System.out.println("Available Categories:");
        for (Category category : categories) {
            System.out.println(category.getName());
        }
    }

    public void displayProducts(Category category) {
        System.out.println("Available Products in " + category.getName() + ":");
        for (Product product : category.getProducts()) {
            System.out.println(product.getName() + " - $" + product.getPrice());
        }
    }

    public void addToCart(Product product) {
        customer.getCart().addItem(product);
    }

    public void removeFromCart(Product product) {
        customer.getCart().removeItem(product);
    }

    public void displayCart() {
        System.out.println("Shopping Cart:");
        List<Product> items = customer.getCart().getItems();
        for (Product item : items) {
            System.out.println(item.getName() + " - $" + item.getPrice());
        }
        System.out.println("Total Cost: $" + customer.getCart().getTotalCost());
    }

    public void startShopping() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the E-commerce Application!");

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer(name, address);
        setCustomer(customer);

        System.out.println("Hello, " + customer.getName() + "!");

        displayCategories();

        boolean shopping = true;
        while (shopping) {
            System.out.print("Enter the category you want to browse (or 'exit' to finish shopping): ");
            String categoryInput = scanner.nextLine();

            if (categoryInput.equalsIgnoreCase("exit")) {
                shopping = false;
                System.out.println("Thank you for shopping!");
                displayCart();
                break;
            }

            Category selectedCategory = null;
            for (Category category : categories) {
                if (category.getName().equalsIgnoreCase(categoryInput)) {
                    selectedCategory = category;
                    break;
                }
            }

            if (selectedCategory != null) {
                displayProducts(selectedCategory);

                boolean selectingProducts = true;
                while (selectingProducts) {
                    System.out.print("Enter the product you want to add to cart (or 'back' to go back): ");
                    String productInput = scanner.nextLine();

                    if (productInput.equalsIgnoreCase("back")) {
                        selectingProducts = false;
                        break;
                    }

                    Product selectedProduct = null;
                    for (Product product : selectedCategory.getProducts()) {
                        if (product.getName().equalsIgnoreCase(productInput)) {
                            selectedProduct = product;
                            break;
                        }
                    }

                    if (selectedProduct != null) {
                        addToCart(selectedProduct);
                        System.out.println(selectedProduct.getName() + " added to cart!");
                    } else {
                        System.out.println("Invalid product selection!");
                    }
                }
            } else {
                System.out.println("Invalid category selection!");
            }
        }

        scanner.close();
    }
}

public class EcommerceApp {
    public static void main(String[] args) {
        ECommerceApplication app = new ECommerceApplication();

        Category electronics = new Category("Electronics");
        electronics.addProduct(new Product("Laptop", 999.99));
        electronics.addProduct(new Product("Smartphone", 699.99));
        electronics.addProduct(new Product("Headphones", 149.99));
        app.addCategory(electronics);

        Category clothing = new Category("Clothing");
        clothing.addProduct(new Product("T-Shirt", 19.99));
        clothing.addProduct(new Product("Jeans", 49.99));
        clothing.addProduct(new Product("Dress", 79.99));
        app.addCategory(clothing);

        app.startShopping();
    }
}
