package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
 * Add_Product_Controller is the controller for the add product page of the
 * application. User input is taken in several fields and a new product is
 * generated. Associated parts can also be added to products.
 */
public class Add_Product_Controller implements Initializable {
    public Button addProductSearch;
    @FXML
    public TextField addProductIdField;
    @FXML
    public TextField addProductNameField;
    @FXML
    public TextField addProductInventoryField;
    @FXML
    public TextField addProductPriceField;
    @FXML
    public TextField addProductMaxField;
    @FXML
    public TextField addProductMinField;
    @FXML
    public TextField addProductSearchField;
    public Button addPartSearch;
    @FXML
    public TextField addPartSearchField;
    @FXML
    private TableView addProductTopTable;
    @FXML
    private TableView addProductBottomTable;
    @FXML
    public Button addProductAddButton;
    @FXML
    public Button addProductSaveButton;
    @FXML
    public Button addProductRemoveAssocPart;
    @FXML
    public TableColumn topAddProductIdColumn;
    @FXML
    public TableColumn topAddProductNameColumn;
    @FXML
    public TableColumn topAddProductPriceColumn;
    @FXML
    public TableColumn topAddProductInventoryColumn;
    @FXML
    public TableColumn bottomAddProductIdColumn;
    @FXML
    public TableColumn bottomAddProductNameColumn;
    @FXML
    public TableColumn bottomAddProductInventoryColumn;
    @FXML
    public TableColumn bottomAddProductPriceColumn;

    Product currentProduct;

    /**
     * initialize sets the parts table with all parts available at the time.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTopTable.setItems(Inventory.getAllParts());

        topAddProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        topAddProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        topAddProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topAddProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * cancelButton redirects the user to the main page without saving user input.
     * @param event
     * @throws IOException
     */
    @FXML
    protected void cancelButton(ActionEvent event) throws IOException {
        Parent cancel_button = FXMLLoader.load(getClass().getResource("/Views/Main_Page.fxml"));
        Scene addPartScene = new Scene(cancel_button);
        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartScene);
        addPartStage.show();
    }

    /**
     * This method takes a search string as an argument, creates an instance of an
     * FX Collections observable Array List, gets all parts, and then uses an enhanced
     * for loop to check each part in the program against the search string, and if a
     * match is found, that part is added to the parts array list. The parts array list
     * is then returned.
     * @param searchString
     * @return
     */
    public ObservableList<Part> addPartsSearchList(String searchString) {
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
     * This method gets the search field text entered by the user, calls the addPartsSearchList
     * method, and populates the returned parts from that method. If that is empty, the method
     * calls AddProductsUpperPartSearchById which searches products by id.
     * @param actionEvent
     */
    public void addPartSearch(ActionEvent actionEvent) {
        String searchFieldValue = addPartSearchField.getText();

        ObservableList<Part> parts = addPartsSearchList(searchFieldValue);

        if(parts.size() == 0) {
            int partId = Integer.parseInt(searchFieldValue);
            Part partIdVar = AddProductsUpperPartsSearchById(partId);
            if(partIdVar != null) {
                parts.add(partIdVar);
            }
        }

        addProductTopTable.setItems(parts);
        addPartSearchField.setText("");
    }

    /**
     * AddProductsUpperPartsSearchById searches by part ID instead name. The id is
     * taken as an argument and passed into this method as a parameter.
     * The id value is then checked in a for loop for the parameter id and
     * each part's id until a match is found.
     * @param id
     * @return
     */
    private Part AddProductsUpperPartsSearchById(int id) {
        ObservableList<Part> addProductAllParts = Inventory.getAllParts();

        for(int i = 0; i < addProductAllParts.size(); i++) {
            Part partById = addProductAllParts.get(i);

            if(partById.getId() == id) {
                return partById;
            }
        }

        return null;
    }

    // Going to need to create an observable list here to populate the bottom
    @FXML
    private ObservableList<Part> associatedPartToProduct = FXCollections.observableArrayList();

    /**
     * addProductAddButton takes a selected part and generates it as an associated part for the product
     * that is being added. The part is selected and assigned to addProductTableAdd, that variable is
     * validated to prevent using the add button if nothing is selected, and the part is added to the
     * bottom table.
     *
     * RUNTIME ERROR - Without the addProductTableAdd == null logic, if a user hadn't selected a part
     * before hitting the add button, a runtime error was thrown. This was solved by adding this logic.
     * @param actionEvent
     */
    @FXML
    public void addProductAddButton(ActionEvent actionEvent) {
        Part addProductTableAdd = (Part) addProductTopTable.getSelectionModel().getSelectedItem();

        if(addProductTableAdd == null) {
            errorAlert(10);
        } else {
            associatedPartToProduct.add(addProductTableAdd);
            addProductBottomTable.setItems(associatedPartToProduct);

            addProductBottomTable.setPlaceholder(new Label("No parts associated with this product"));
            bottomAddProductIdColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
            bottomAddProductPriceColumn.setCellValueFactory(new PropertyValueFactory<Part , Double>("price"));
            bottomAddProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
            bottomAddProductNameColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
            addProductBottomTable.refresh();

        }

    }

    /**
     * This method removes parts from the bottom table, the associated parts table.
     * Logic prevents using the remove button unless a part is selected. An alert is also
     * generated to confirm a user's selection, that they really want to remove the part.
     * @param actionEvent
     */
    public void addProductRemoveAssocPart(ActionEvent actionEvent) {
        Part part = (Part) addProductBottomTable.getSelectionModel().getSelectedItem();

        if (part == null) {
            errorAlert(5);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Products");
            alert.setHeaderText("Remove");
            alert.setContentText("Do you want to remove this associated part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedPartToProduct.remove(part);
            }
        }

    }

    /**
     * addProductSaveButton validates all user input to ensure correct data types, prevent empty fields, control
     * min, max, and inventory rules (inventory must fall between min and max, min is less than max), and
     * if all validation passes, a new part is created using the Inventory model. It also prevents users from
     * creating products where the price is less than the sum of all parts' prices.
     * @param actionEvent
     * @throws IOException
     */
    public void addProductSaveButton(ActionEvent actionEvent) throws IOException {

        int productId = (int) (Math.random() * (1000000 - 1) + 1);

        if(addProductNameField.getText().trim().isEmpty() || addProductInventoryField.getText().trim().isEmpty() || addProductMaxField.getText().trim().isEmpty() ||
                addProductMinField.getText().trim().isEmpty() || addProductPriceField.getText().trim().isEmpty()) {
            errorAlert(1);
        } else {

            if (productFieldTypeValidation(addProductInventoryField.getText(), addProductPriceField.getText(), addProductMinField.getText(), addProductMaxField.getText())) {
                String productName = String.valueOf(addProductNameField.getText());
                Integer productInventory = Integer.valueOf(addProductInventoryField.getText());
                Integer productMax = Integer.valueOf(addProductMaxField.getText());
                Integer productMin = Integer.valueOf(addProductMinField.getText());
                Double productPrice = Double.valueOf(addProductPriceField.getText());
                if (productMax < productMin) {
                    errorAlert(2);
                } else {
                    if (productInventory > productMax || productInventory < productMin) {
                        errorAlert(3);
                    } else {

                        currentProduct = new Product(productId, productName, productInventory, productPrice, productMin, productMax);

                        double associatedPartsTotalPriceByParts = 0.0;

                        for (Part part : associatedPartToProduct) {
                            currentProduct.addAssociatedPart(part);
                            associatedPartsTotalPriceByParts += part.getPrice();
                        }

                        if (productPrice < associatedPartsTotalPriceByParts) {
                            errorAlert(4);
                        } else {
                            Inventory.addProduct(currentProduct);

                            Parent saveButton = FXMLLoader.load(getClass().getResource("/Views/Main_Page.fxml"));
                            Scene addPartScene = new Scene(saveButton);
                            Stage addPartStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            addPartStage.setScene(addPartScene);
                            addPartStage.show();
                        }

                    }

                }

            }

        }

    }

    /**
     * This method validates data types for inventory, price, min, and max fields. If a user inputs
     * a string value instead of a number, an error is generated directing the user to the specific
     * field that caused an issue.
     * @param productInventory
     * @param productPrice
     * @param productMin
     * @param productMax
     * @return
     */
    public boolean productFieldTypeValidation(String productInventory, String productPrice, String productMin, String productMax) {

        boolean validationResult = true;

        try {
            Integer inventoryValidation = Integer.parseInt(productInventory);
        } catch (Exception e) {
            errorAlert(6);
            validationResult = false;
        }

        try {
            Double priceValidation = Double.valueOf(productPrice);
        } catch (Exception e) {
            errorAlert(7);
            validationResult = false;
        }

        try {
            Integer minValidation = Integer.parseInt(productMin);
        } catch (Exception e) {
            errorAlert(8);
            validationResult = false;
        }

        try {
            Integer maxValidation = Integer.parseInt(productMax);
        } catch (Exception e) {
            errorAlert(9);
            validationResult = false;
        }

        return validationResult;

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
            alert.setHeaderText("Required Field is Blank");
            alert.setContentText("All fields are required to create a new product. Please fill in all fields and try again.");
            alert.showAndWait();
        } else if(errorCode == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Minimum and Maximum Inventory Values Error");
            alert.setContentText("The minimum inventory number must be less than the maximum.");
            alert.showAndWait();
        } else if(errorCode == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Inventory Value Error");
            alert.setContentText("Inventory cannot be less than Min or more than Max.");
            alert.showAndWait();
        } else if(errorCode == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Price Value Error");
            alert.setContentText("The product price cannot be less than the sum of its parts.");
            alert.showAndWait();
        } else if(errorCode == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Remove Product Error");
            alert.setContentText("Please select a product to remove.");
            alert.showAndWait();
        } else if(errorCode == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User value error");
            alert.setContentText("Inventory field must be an integer.");
            alert.showAndWait();
        } else if(errorCode == 7) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User value error");
            alert.setContentText("Price field must be a number.");
            alert.showAndWait();
        } else if(errorCode == 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User value error");
            alert.setContentText("Minimum field must be an integer.");
            alert.showAndWait();
        } else if(errorCode == 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User value error");
            alert.setContentText("Maximum field must be an integer.");
            alert.showAndWait();
        } else if(errorCode == 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Add Part Error");
            alert.setContentText("Please select a part to add.");
            alert.showAndWait();
        }

    }

}
