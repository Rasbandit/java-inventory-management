package model;

/** An abstract class for shared features of a Part. */
abstract public class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Part Constructor */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Sets the id of the current Part */
    public void setId(int id) {
        this.id = id;
    }

    /** Sets the name of the current Part */
    public void setName(String name) {
        this.name = name;
    }

    /** Sets the price of the current Part */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Sets the stock of the current Part */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Sets the min of the current Part */
    public void setMin(int min) {
        this.min = min;
    }

    /** Sets the max of the current Part */
    public void setMax(int max) {
        this.max = max;
    }

    /** Gets the id of the current Part */
    public int getId() {
        return this.id;
    }

    /** Gets the name of the current Part */
    public String getName() {
        return this.name;
    }

    /** Gets the price of the current Part */
    public double getPrice() {
        return this.price;
    }

    /** Gets the stock of the current Part */
    public int getStock() {
        return this.stock;
    }

    /** Gets the min of the current Part */
    public int getMin() {
        return this.min;
    }

    /** Gets the max of the current Part */
    public int getMax() {
        return this.max;
    }
}
