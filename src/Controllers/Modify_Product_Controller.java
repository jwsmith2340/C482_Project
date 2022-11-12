package Controllers;

import Model.*;
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
 * Modify_Product_Controller is the controller for the modify product page of the
 * application. User input is taken in several fields if the
 *  * user wishes to make changes, and the existing part is updated.
 */
public class Modify_Product_Controller implements Initializable {
    @FXML
    public TextField modifyProductNameField;
    @FXML
    public TextField modifyProductInventoryField;
    @FXML
    public TextField modifyProductPriceField;
    @FXML
    public TextField modifyProductMaxField;
    @FXML
    public TextField modifyProductMinField;
    @FXML
    public TableColumn topModifyProductIdColumn;
    @FXML
    public TableColumn topModifyProductNameColumn;
    @FXML
    public TableColumn topModifyProductInventoryColumn;
    @FXML
    public TableColumn topModifyProductPriceColumn;
    @FXML
    public TableColumn bottomModifyProductIdColumn;
    @FXML
    public TableColumn bottomModifyProductNameColumn;
    @FXML
    public TableColumn bottomModifyProductInventoryColumn;
    @FXML
    public TableColumn bottomModifyProductPriceColumn;
    @FXML
    public TableView<Part> modifyProductBottomTable;
    @FXML
    public TableView<Part> modifyProductTopTable;
    @FXML
    public Button modifyProductAddButton;
    @FXML
    public TextField modifyProductIdField;
    public Button modifyProductSave;

    @FXML
    private ObservableList<Part> associatedPartToProduct = FXCollections.observableArrayList();
    public Button modifyProductRemoveAssocPart;
    public Button modifyProductSearch;
    public TextField modifyProductSearchField;
    public Button modifyPartSearch;
    @FXML
    public TextField modifyPartSearchField;

    Product modifyProduct;
    Inventory inventory;

    /**
     * setPart takes the part selected on the main page as an argument and
     * passes it into the method as a parameter. part is then assigned to the
     * variable modifyPart to enhance developer readability. The part fields are
     * then pre-filled out so the user can see the part's current values that
     * they are wanting to modify.
     * @param product
     */
    void setProduct(Product product) {

        modifyProduct = product;

        modifyProductIdField.setText(Integer.toString(modifyProduct.getId()));
        modifyProductNameField.setText(modifyProduct.getName());
        modifyProductPriceField.setText(Double.toString(modifyProduct.getPrice()));
        modifyProductMaxField.setText(Integer.toString(modifyProduct.getMax()));
        modifyProductMinField.setText(Integer.toString(modifyProduct.getMin()));
        modifyProductInventoryField.setText(Integer.toString(modifyProduct.getStock()));

        associatedPartToProduct.setAll(modifyProduct.getAllAssociatedParts());

    }

    /**
     * The modify_part_controller's inventory variable is set as the inventory that is
     * passed in to this method.
     * @param inv
     */
    public void getProductInventoryModify(Inventory inv) {
        this.inventory = inv;
    }

    /**
     * Initialize sets all parts to the upper table, the all parts table. The bottom
     * parts table is set to the associatePartToProduct value, which was assigned when
     * the setProduct method is called.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductTopTable.setItems(Inventory.getAllParts());

        topModifyProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        topModifyProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        topModifyProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        topModifyProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductBottomTable.setItems(associatedPartToProduct);

        bottomModifyProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bottomModifyProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        bottomModifyProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        bottomModifyProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
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
    public ObservableList<Part> modifyPartsSearchList(String searchString) {
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
    public void modifyPartSearch(ActionEvent actionEvent) {
        String searchFieldValue = modifyPartSearchField.getText();

        ObservableList<Part> parts = modifyPartsSearchList(searchFieldValue);

        if(parts.size() == 0) {
            int partId = Integer.parseInt(searchFieldValue);
            Part partIdVar = AddProductsUpperPartsSearchById(partId);
            if(partIdVar != null) {
                parts.add(partIdVar);
            }
        }

        modifyProductTopTable.setItems(parts);
        modifyPartSearchField.setText("");
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

    /**
     * modifyProductAddButton takes a selected part and generates it as an associated part for the product
     * that is being added. The part is selected and assigned to addProductTableAdd, that variable is
     * validated to prevent using the add button if nothing is selected, and the part is added to the
     * bottom table.
     *
     * RUNTIME ERROR - Without the modifyProductTableAdd == null logic, if a user hadn't selected a part
     * before hitting the add button, a runtime error was thrown. This was solved by adding this logic.
     * @param actionEvent
     */
    @FXML
    public void modifyProductAddButton(ActionEvent actionEvent) {
        Part modifyProductTableAdd = modifyProductTopTable.getSelectionModel().getSelectedItem();

        if(modifyProductTableAdd == null) {
            errorAlert(10);
        } else {
            associatedPartToProduct.add(modifyProductTableAdd);
            modifyProductBottomTable.setItems(associatedPartToProduct);

            modifyProductBottomTable.setPlaceholder(new Label("No parts associated with this product"));
            bottomModifyProductIdColumn.setCellValueFactory(new PropertyValueFactory<Part,Integer>("id"));
            bottomModifyProductPriceColumn.setCellValueFactory(new PropertyValueFactory<Part , Double>("price"));
            bottomModifyProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
            bottomModifyProductNameColumn.setCellValueFactory(new PropertyValueFactory<Part,String>("name"));
            modifyProductBottomTable.refresh();
        }

    }

    /**
     * This method removes parts from the bottom table, the associated parts table.
     * Logic prevents using the remove button unless a part is selected. An alert is also
     * generated to confirm a user's selection, that they really want to remove the part.
     *
     * RUNTIME ERROR - Without the logic preventing a user from removing a part without a
     * part selected, a runtime error is thrown. This runtime error was corrected by
     * adding logic to prevent a user from using the remove button without a part selected.
     * @param actionEvent
     */
    public void modifyProductRemoveAssocPart(ActionEvent actionEvent) {
        Part part = modifyProductBottomTable.getSelectionModel().getSelectedItem();

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
     * modifyProductSave validates all user input to ensure correct data types, prevent empty fields, control
     * min, max, and inventory rules (inventory must fall between min and max, min is less than max), and
     * if all validation passes, a new part is created using the Inventory model. It also prevents users from
     * modifying a product such that the price is less than the sum of all parts' prices.
     * @param actionEvent
     * @throws IOException
     */
    public void modifyProductSave(ActionEvent actionEvent) throws IOException {

        if(modifyProductNameField.getText().trim().isEmpty() || modifyProductInventoryField.getText().trim().isEmpty() || modifyProductMaxField.getText().trim().isEmpty() ||
                modifyProductMinField.getText().trim().isEmpty() || modifyProductPriceField.getText().trim().isEmpty()) {
            errorAlert(1);
        } else {

            if (productFieldTypeValidation(modifyProductInventoryField.getText(), modifyProductPriceField.getText(), modifyProductMinField.getText(), modifyProductMaxField.getText())) {
                Integer productId = Integer.valueOf(modifyProductIdField.getText());
                String productName = String.valueOf(modifyProductNameField.getText());
                Integer productInventory = Integer.valueOf(modifyProductInventoryField.getText());
                Integer productMax = Integer.valueOf(modifyProductMaxField.getText());
                Integer productMin = Integer.valueOf(modifyProductMinField.getText());
                Double productPrice = Double.valueOf(modifyProductPriceField.getText());
                if (productMax < productMin) {
                    errorAlert(2);
                } else {
                    if (productInventory > productMax || productInventory < productMin) {
                        errorAlert(3);
                    } else {

                        Product modifiedProduct = new Product(productId, productName, productInventory, productPrice, productMin, productMax);

                        double associatedPartsTotalPriceByParts = 0.0;

                        for (Part part : associatedPartToProduct) {
                            associatedPartsTotalPriceByParts += part.getPrice();
                        }

                        if (productPrice < associatedPartsTotalPriceByParts) {
                            errorAlert(4);
                        } else {

                            inventory.updateProduct(modifiedProduct);

                            for (int i = 0; i < associatedPartToProduct.size(); i++) {
                                modifiedProduct.addAssociatedPart(associatedPartToProduct.get(i));
                            }

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
            alert.setContentText("All fields are required to create a new part. Please fill in all fields and try again.");
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
