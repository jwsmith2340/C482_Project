package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Class for creating Product objects
 */
public class Product {

    private int id;
    private String name;
    private int stock;
    private double price;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Class constructor, this instantiates a Product object
     * @param id
     * @param name
     * @param stock
     * @param price
     * @param min
     * @param max
     */
    public Product(int id, String name, int stock, double price, int min, int max) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /**
     * gets product id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * sets product id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets product name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * sets product name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets product stock
     * @return
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets product stock
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * gets product price
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets product price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * gets product min
     * @return
     */
    public int getMin() {
        return min;
    }

    /**
     * sets product min
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * gets product max
     * @return
     */
    public int getMax() {
        return max;
    }

    /**
     * sets product max
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * adds an associated part
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * deletes an associated part
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatePart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * returns all associated parts
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
