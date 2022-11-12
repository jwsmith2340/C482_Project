package Controllers;

import Model.InHouse_Model;
import Model.Inventory;
import Model.Outsourced_Model;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Add_Part_Controller is the controller for the add part page of the
 * application. User input is taken in several fields and a new part is
 * generated.
 */
public class Add_Part_Controller implements Initializable {

    @FXML
    public RadioButton addPartInHouse;
    @FXML
    public ToggleGroup addPartToggleGroup;
    @FXML
    public Button addPartSave;
    @FXML
    public RadioButton addPartOutsourced;
    @FXML
    public TextField addPartIdField;
    @FXML
    public TextField addPartNameField;
    @FXML
    public TextField addPartInventoryField;
    @FXML
    public TextField addPartPriceField;
    @FXML
    public TextField addPartMaxField;
    @FXML
    public TextField addPartMinField;
    @FXML
    public TextField addPartMachineIdField;
    @FXML
    public Label inHouseOutsourcedVarField;

    /**
     * Initialize is created as a developer checkpoint to ensure a properly
     * functioning application.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized in Add Part Controller");
    }

    /**
     * cancelButton redirects the application to the main page without saving
     * any user input.
     *
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
     * addPartSave validates all user input to ensure correct data types, prevent empty fields, control
     * min, max, and inventory rules (inventory must fall between min and max, min is less than max), and
     * if all validation passes, a new part is created using the Inventory model.
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void addPartSave(ActionEvent actionEvent) throws IOException {

        int partId = (int) (Math.random() * (1000000 - 1) + 1);

        if(addPartNameField.getText().trim().isEmpty() || addPartInventoryField.getText().trim().isEmpty() || addPartMaxField.getText().trim().isEmpty() ||
            addPartMinField.getText().trim().isEmpty() || addPartPriceField.getText().trim().isEmpty()) {
            errorAlert(1);
        } else {

            if (partFieldTypeValidation(addPartInventoryField.getText(), addPartPriceField.getText(), addPartMinField.getText(), addPartMaxField.getText())) {
                String partName = String.valueOf(addPartNameField.getText());
                Integer partInventory = Integer.valueOf(addPartInventoryField.getText());
                Integer partMax = Integer.valueOf(addPartMaxField.getText());
                Integer partMin = Integer.valueOf(addPartMinField.getText());
                Double partPrice = Double.valueOf(addPartPriceField.getText());

                if (addPartInHouse.isSelected()) {
                    if (addPartMachineIdField.getText().trim().isEmpty()) {
                        errorAlert(1);
                    } else {
                        if (partMax < partMin) {
                            errorAlert(2);
                        } else {
                            if (partInventory > partMax || partInventory < partMin) {
                                errorAlert(3);
                            } else {

                                try {

                                    Integer partMachineId = Integer.valueOf(addPartMachineIdField.getText());
                                    Inventory.addPart(new InHouse_Model(partId, partName, partInventory, partPrice, partMin, partMax, partMachineId));

                                    Parent saveButton = FXMLLoader.load(getClass().getResource("/Views/Main_Page.fxml"));
                                    Scene addPartScene = new Scene(saveButton);
                                    Stage addPartStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                    addPartStage.setScene(addPartScene);
                                    addPartStage.show();

                                } catch(Exception e) {

                                    errorAlert(8);

                                }
                            }
                        }
                    }
                } else if (addPartOutsourced.isSelected()) {
                    if (addPartMachineIdField.getText().trim().isEmpty()) {
                        errorAlert(1);
                    } else {
                        if (partMax < partMin) {
                            errorAlert(2);
                        } else {
                            if (partInventory > partMax || partInventory < partMin) {
                                errorAlert(3);
                            } else {
                                if (addPartMachineIdField.getText().trim().isEmpty()) {
                                    errorAlert(1);
                                } else {
                                    String partCompany = String.valueOf(addPartMachineIdField.getText());

                                    Inventory.addPart(new Outsourced_Model(partId, partName, partInventory, partPrice, partMin, partMax, partCompany));

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

        }

    }

    /**
     * Sets the text of the inhouse/outsourced user input field appropriately
     * depending on the currently selected radio button, inhouse or outsourced.
     * @param actionEvent
     */
    public void addPartInHouse(ActionEvent actionEvent) {
        inHouseOutsourcedVarField.setText("Machine ID");
    }

    /**
     * Sets the text of the inhouse/outsourced user input field appropriately
     * depending on the currently selected radio button, inhouse or outsourced.
     * @param actionEvent
     */
    public void addPartOutsourced(ActionEvent actionEvent) {
        inHouseOutsourcedVarField.setText("Company");
    }

    /**
     * This method validates data types for inventory, price, min, and max fields. If a user inputs
     * a string value instead of a number, an error is generated directing the user to the specific
     * field that caused an issue.
     *
     * @param partInventory
     * @param partPrice
     * @param partMin
     * @param partMax
     * @return
     */
    public boolean partFieldTypeValidation(String partInventory, String partPrice, String partMin, String partMax) {

        boolean validationResult = true;

        try {
            Integer inventoryValidation = Integer.parseInt(partInventory);
        } catch (Exception e) {
            errorAlert(4);
            validationResult = false;
        }

        try {
            Double priceValidation = Double.valueOf(partPrice);
        } catch (Exception e) {
            errorAlert(5);
            validationResult = false;
        }

        try {
            Integer minValidation = Integer.parseInt(partMin);
        } catch (Exception e) {
            errorAlert(6);
            validationResult = false;
        }

        try {
            Integer maxValidation = Integer.parseInt(partMax);
        } catch (Exception e) {
            errorAlert(7);
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
            alert.setHeaderText("User value error");
            alert.setContentText("Inventory field must be an integer.");
            alert.showAndWait();
        } else if(errorCode == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User value error");
            alert.setContentText("Price field must be a number.");
            alert.showAndWait();
        } else if(errorCode == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User value error");
            alert.setContentText("Minimum field must be an integer.");
            alert.showAndWait();
        } else if(errorCode == 7) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User value error");
            alert.setContentText("Maximum field must be an integer.");
            alert.showAndWait();
        } else if(errorCode == 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("User value error");
            alert.setContentText("Machine ID field must be an integer.");
            alert.showAndWait();
        }

    }

}