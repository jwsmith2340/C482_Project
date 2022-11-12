package Controllers;

import Model.InHouse_Model;
import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main_Page_Controller is the main page of the application. Initializable
 * is implemented to load data into the page so the user isn't greeted by
 * an unpopulated part and product table.
 */
public class Main_Page_Controller implements Initializable {

    public TextField mainPartSearchField;
    public TextField mainProductSearchField;
    public Button mainPartsSearch;
    public Label theLabel;
    public Button mainPartsAdd;
    public Button mainPartsDelete;
    public Button mainPartsModify;
    public Button mainProductsSearch;
    public Button mainProductsDelete;
    public Button mainProductsModify;
    public Button mainProductsAdd;
    public Button mainExit;
    @FXML
    private TableView<Part> mainPartTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TextField partSearch;
    @FXML
    private TableView<Product> mainProductTable;
    @FXML
    private TableColumn<Part, Integer> productIdColumn;
    @FXML
    private TableColumn<Part, String> productNameColumn;
    @FXML
    private TableColumn<Part, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Part, Double> productPriceColumn;
    @FXML
    private TextField productSearch;




    private static Boolean trigger = true;
    Inventory inventory;

    /**
     * The addData method sets developer specified part and product objects to populate the part
     * and product tables on the main page.
     *
     * RUNTIME ERROR - I experienced a runtime error by trying to create new Part( objects instead of
     * InHouse_Model objects. I had forgotten that the Part class was the super class of InHouse and
     * Outsourced. I changed Part( to InHouse_Model( and fixed the error.
     */
    private void addData() {

        if(!trigger) {
            return;
        } else {
            trigger = false;

            Part brakes = new InHouse_Model(1, "Brakes", 10, 15.99, 0, 20, 1);
            Part wheel = new InHouse_Model(2, "Wheel", 16, 11.99, 0, 20, 2);
            Part seat = new InHouse_Model(3, "Seat", 15, 15.99, 0, 20, 3);
            Inventory.addPart(brakes);
            Inventory.addPart(wheel);
            Inventory.addPart(seat);

            Product giant_bike = new Product(1000, "Giant Bike", 5, 299.99, 0, 20);
            Product tricycle = new Product(1001, "Tricycle", 3, 99.99, 0, 20);
            Inventory.addProduct(giant_bike);
            Inventory.addProduct(tricycle);
        }
    }

    /**
     * initialize sets the mainPartTable and mainProductTable with the data generated when
     * I call the addData() method.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addData();

        mainPartTable.setItems(Inventory.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductTable.setItems(Inventory.getAllProducts());

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * mainPartsAddClick directs the user to the Add Parts window in the application.
     *
     * RUNTIME ERROR - I threw a runtime error initially by forgetting to capitalize
     * 'Views' in the path string to create the Parent add_part. This was corrected
     * by capitalizing Views.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    protected void mainPartsAddClick(ActionEvent event) throws IOException {
        Parent add_part = FXMLLoader.load(getClass().getResource("/Views/Add_Part.fxml"));
        Scene addPartScene = new Scene(add_part);
        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    /**
     * mainPartsModifyClick directs the user to the modify parts page.
     *
     * RUNTIME ERROR - I had placed the if statement that required selected text,
     * but I initially forgot to add the else statement. This caused a runtime error
     * that was solved by adding the else statement and errorAlert call.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    protected void mainPartsModifyClick(ActionEvent event) throws IOException {

        if(mainPartTable.getSelectionModel().getSelectedItem() != null) {
            Part selectedPart = mainPartTable.getSelectionModel().getSelectedItem();
            Parent parent;
            Stage stage;
            stage = (Stage) mainPartsModify.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Modify_Part.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            Modify_Part_Controller controller = loader.getController();
            controller.setPart(selectedPart);
            controller.getPartInventoryModify(inventory);
        } else {
            errorAlert(2);
        }
    }

    /**
     * This deletes parts from the parts table.
     *
     * RUNTIME ERROR - A runtime error was thrown the first time I tried to delete
     * without selecting a part. This was solved by adding the if(part == null) logic
     * to the code.
     */
    @FXML
    protected void mainPartsDeleteClick() {
        Part part = mainPartTable.getSelectionModel().getSelectedItem();
        if(part == null) {
            errorAlert(4);
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Parts");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(part);
            }

        }

    }

    /**
     * This method gets the search field text entered by the user, calls the mainPartsSearchList
     * method, and populates the returned parts from that method. If that is empty, the method
     * calls mainPartsSearchById which searches products by id.
     * @param actionEvent
     */
    public void mainPartsSearchClick(ActionEvent actionEvent) {
        String searchFieldValue = mainPartSearchField.getText();

        ObservableList<Part> parts = mainPartsSearchList(searchFieldValue);

        if(parts.size() == 0) {
            int partId = Integer.parseInt(searchFieldValue);
            Part partIdVar = mainPartsSearchById(partId);
            if(partIdVar != null) {
                parts.add(partIdVar);
            }
        }

        mainPartTable.setItems(parts);
        mainPartSearchField.setText("");
    }

    /**
     * This method takes a search string as an argument, creates an instance of an
     * FX Collections observable Array List, gets all parts, and then uses an enhanced
     * for loop to check each part in the program against the search string, and if a
     * match is found, that part is added to the parts array list. The parts array list
     * is then returned.
     *
     * @param searchString
     * @return
     */
    public ObservableList<Part> mainPartsSearchList(String searchString) {
        ObservableList<Part> parts = FXCollections.observableArrayList();

        ObservableList<Part> mainAllParts = Inventory.getAllParts();

        for(Part part : mainAllParts) {
            if(part.getName().contains(searchString)) {
                parts.add(part);
            }
        }

        return parts;
    }

    /**
     * mainPartsSearchById searches by part ID instead name. The id is
     * taken as an argument and passed into this method as a parameter.
     * The id value is then checked in a for loop for the parameter id and
     * each part's id until a match is found.
     *
     * @param id
     * @return
     */
    private Part mainPartsSearchById(int id) {
        ObservableList<Part> mainAllParts = Inventory.getAllParts();

        for(int i = 0; i < mainAllParts.size(); i++) {
            Part partById = mainAllParts.get(i);

            if(partById.getId() == id) {
                return partById;
            }
        }

        return null;
    }

    /**
     * mainProductsAddClick directs the user to the Add Product window in the application.
     * @param event
     * @throws IOException
     */
    @FXML
    protected void mainProductsAddClick(ActionEvent event) throws IOException {
        Parent add_product = FXMLLoader.load(getClass().getResource("/Views/Add_Product.fxml"));
        Scene addPartScene = new Scene(add_product);
        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    /**
     * mainProductsModifyClick directs the user to the modify products page.
     * @param event
     * @throws IOException
     */
    @FXML
    protected void mainProductsModifyClick(ActionEvent event) throws IOException {
        if(mainProductTable.getSelectionModel().getSelectedItem() != null) {
            Product selectedProduct = mainProductTable.getSelectionModel().getSelectedItem();
            Parent parent;
            Stage stage;
            stage = (Stage) mainPartsModify.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Modify_Product.fxml"));
            parent = loader.load();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            Modify_Product_Controller controller = loader.getController();
            controller.setProduct(selectedProduct);
            controller.getProductInventoryModify(inventory);
        } else {
            errorAlert(3);
        }
    }

    /**
     * This deletes products from the product table.
     *
     * RUNTIME ERROR - Even after solving the same error in the
     * mainPartsDeleteClick method, I forgot to include logic to prevent
     * deleting when no product was selected. This was corrected by adding
     * that logic.
     */
    @FXML
    protected void mainProductsDeleteClick() {

        Product product = mainProductTable.getSelectionModel().getSelectedItem();

        if (product == null) {
            errorAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Products");
            alert.setHeaderText("Delete");
            alert.setContentText("Do you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (product.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(product);
                } else {
                    errorAlert(1);
                }
            }

        }

    }

    /**
     * This method searches the users input string by calling the mainProductsSearchList
     * method. If nothing is returned, the input string is checked as an ID by calling
     * the mainProductsSearchById method.
     * @param actionEvent
     */
    public void mainProductsSearchClick(ActionEvent actionEvent) {
        String searchFieldValue = mainProductSearchField.getText();

        ObservableList<Product> products = mainProductsSearchList(searchFieldValue);

        if(products.size() == 0) {
            int productId = Integer.parseInt(searchFieldValue);
            Product productIdVar = mainProductsSearchById(productId);
            if(productIdVar != null) {
                products.add(productIdVar);
            }
        }

        mainProductTable.setItems(products);
        mainProductSearchField.setText("");
    }

    /**
     * mainProductsSearchList creates an FX Collections array list called products. All
     * products are then assigned to mainAllProducts and an enhanced for loop is run on
     * mainAllProducts. Each product is compared to the search string, and if found, that
     * product is added to the products array list, which is returned.
     * @param searchString
     * @return
     */
    public ObservableList<Product> mainProductsSearchList(String searchString) {
        ObservableList<Product> products = FXCollections.observableArrayList();

        ObservableList<Product> mainAllProducts = Inventory.getAllProducts();

        for(Product product : mainAllProducts) {
            if(product.getName().contains(searchString)) {
                products.add(product);
            }
        }

        return products;
    }

    /**
     * This method takes an id as an argument and passes it into the method as
     * a parameter. A for loop is set up to check the ID of all products against
     * the id provided. When a match is made, that product is returned.
     * @param id
     * @return
     */
    private Product mainProductsSearchById(int id) {
        ObservableList<Product> mainAllProducts = Inventory.getAllProducts();

        for(int i = 0; i < mainAllProducts.size(); i++) {
            Product productById = mainAllProducts.get(i);

            if(productById.getId() == id) {
                return productById;
            }
        }

        return null;
    }

    /**
     * Exits the application.
     */
    @FXML
    protected void mainExitClick() {
        Platform.exit();
    }

    /**
     * errorAlert takes an errorCode as an argument and passes that value
     * into the method as a parameter. The error code is checked in a series
     * of if/else statements and triggers an alert whenever a match is found.
     * @param errorCode
     */
    private void errorAlert(int errorCode) {
        if(errorCode == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Associated Parts");
            alert.setContentText("This product has associated parts.");
            alert.showAndWait();
        } else if(errorCode == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Modify Part Error");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
        } else if(errorCode == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Modify Product Error");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
        } else if(errorCode == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Delete Part Error");
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();
        } else if(errorCode == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Delete Product Error");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }

    }

}
