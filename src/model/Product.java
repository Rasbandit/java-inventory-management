package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** A product class that contains a lost of multiple Parts. */
public class Product {
    int id;
    String name;
    double price;
    int stock;
    int min;
    int max;
    ObservableList<Part> associatedParts;

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    static {
        init();
    }

    /** Sets up test data. */
    private static void init() {
        if(allProducts.size() == 0) {
            ObservableList<Part> partsOne = FXCollections.observableArrayList();
            partsOne.add(Outsourced.getAllOutsourced().get(0));
            allProducts.add(new Product(1, "car", 24d, 1, 1, 10, partsOne));

            ObservableList<Part> partsTwo = FXCollections.observableArrayList();
            partsTwo.add(Outsourced.getAllOutsourced().get(1));
            allProducts.add(new Product(2, "boat", 22d, 2, 1, 10, partsTwo));

            ObservableList<Part> partsThree = FXCollections.observableArrayList();
            partsThree.add(InHouse.getAllInHouse().get(1));
            allProducts.add(new Product(3, "bat", 29d, 2, 1, 10, partsThree));
        }
    }

    /** Returns all Products */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /** Deletes the given product from allProducts */
    public static void deleteProduct(Product selectedProduct) {
        for (Integer i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == selectedProduct.getId()) {
                allProducts.remove(allProducts.get(i));
            }
        }
    }

    /** Product Constructor. */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList parts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = parts;
    }

    /** Gets the id of the current Product */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /** Sets the id of the current Product */
    public void setId(int id) {
        this.id = id;
    }

    /** Sets the name of the current Product */
    public void setName(String name) {
        this.name = name;
    }

    /** Sets the price of the current Product */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Sets the stock of the current Product */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Sets the min of the current Product */
    public void setMin(int min) {
        this.min = min;
    }

    /** Sets the max of the current Product */
    public void setMax(int max) {
        this.max = max;
    }

    /** Gets the id of the current Product */
    public int getId() {
        return this.id;
    }

    /** Gets the name of the current Product */
    public String getName() {
        return this.name;
    }

    /** Gets the price of the current Product */
    public double getPrice() {
        return this.price;
    }

    /** Gets the stock of the current Product */
    public int getStock() {
        return this.stock;
    }

    /** Gets the min of the current Product */
    public int getMin() {
        return this.min;
    }

    /** Gets the Max of the current Product */
    public int getMax() {
        return this.max;
    }

    /** add a Part to the current Product */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /** Removes a part from the current product. */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        allProducts.remove(selectedAssociatedPart);
        return true;
    }

    /** Returns all Associated parts for the given Product */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
