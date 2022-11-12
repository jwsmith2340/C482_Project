package Model;

/**
 * Class given by WGU in the Supporting Documents
 */
public abstract class Part {
    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;

    private static int uniqueId = 4;


    /**
     * Class constructor, this instantiates an object. This is the super class used by
     * InHouse_Model and Outsourced_Model.
     * @param id
     * @param name
     * @param stock
     * @param price
     * @param min
     * @param max
     */
    public Part(int id, String name, int stock, double price, int min, int max) {
    // IDE made me put void, instructor doesn't have void, make sure with this, might be a but source later
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /**
     * Sets the part id
     * @param id
     */
    public void setId(int id) {
        this.id = uniqueId++;
    }

    /**
     * sets the part name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the part stock
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * sets the part price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * sets the part min
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * sets the part max
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * gets the part id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * gets the part name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * gets the part stock
     * @return
     */
    public int getStock() {
        return stock;
    }

    /**
     * gets the part min
     * @return
     */
    public int getMin() {
        return min;
    }

    /**
     * gets the part max
     * @return
     */
    public int getMax() {
        return max;
    }

    /**
     * gets the part price
     * @return
     */
    public double getPrice() {
        return price;
    }

}
