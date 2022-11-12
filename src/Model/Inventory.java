package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for accessing add, update, delete, getAll, and lookup methods for
 * parts and products.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Method looks up a part by id
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId) {

        for (Part part : allParts) {
            if (part.getId() == partId) return part;
        }

        return null;
    }

    /**
     * Adds a part to the allParts Array List
     * @param part
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * Updates a part, takes in the part a user wants to update and
     * finds the id of the original part and sets the original part
     * to the updated part's values.
     * @param partToUpdate
     */
    public static void updatePart(Part partToUpdate) {
        for(int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == partToUpdate.getId()) {
                allParts.set(i, partToUpdate);
                break;
            }
        }
        return;
    }

    /**
     * Deletes a part from the allParts Array List
     * @param part
     */
    public static void deletePart(Part part) {
        allParts.remove(part);
    }

    /**
     * Gets all parts from the allParts Array List
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Method looks up a part by id
     * @param productId
     * @return
     */
    public static Product lookupProduct(int productId) {

        for (Product product : allProducts) {
            if (product.getId() == productId) return product;
        }

        return null;
    }

    /**
     * Adds a product to the allProducts Array List
     * @param product
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Updates a product, takes in the product a user wants to update and
     * finds the id of the original product and sets the original product
     * to the updated product's values.
     * @param productToUpdate
     */
    public static void updateProduct(Product productToUpdate) {
        for(int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId() == productToUpdate.getId()) {
                allProducts.set(i, productToUpdate);
                break;
            }
        }
        return;
    }

    /**
     * Deletes a product from the allProducts Array List
     * @param product
     */
    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    /**
     * Gets all products from the allProducts Array List
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
