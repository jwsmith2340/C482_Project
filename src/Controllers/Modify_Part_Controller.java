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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Modify_Part_Controller is the controller for the modify part page
 * of this application. User input is taken in several fields if the
 * user wishes to make changes, and the existing part is updated.
 */
public class Modify_Part_Controller implements Initializable {
    @FXML
    public RadioButton modifyPartInHouse;
    @FXML
    public ToggleGroup modifyPartToggleGroup;
    @FXML
    private RadioButton modifyPartOutsourced;
    @FXML
    private TextField modifyPartIdField;
    @FXML
    private TextField modifyPartNameField;
    @FXML
    private TextField modifyPartInventoryField;
    @FXML
    private TextField modifyPartPriceField;
    @FXML
    private TextField modifyPartMaxField;
    @FXML
    private TextField modifyPartMinField;
    @FXML
    private TextField modifyPartMachineIdField;
    @FXML
    public Label inHouseOutsourcedVarFieldModify;
    @FXML
    public Button modifyPartSave;

    /**
     * Initialize is created as a developer checkpoint to ensure a properly
     * functioning application.
     *
     * RUNTIME ERROR - I tried removing this initialize method, and a runtime error was
     * thrown, because the Modify_Part_Controller class expects it. This error was fixed
     * by restoring this method.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized in Modify_Part_Controller");
    }

    Part modifyPart;
    Inventory inventory;

    /**
     * setPart takes the part selected on the main page as an argument and
     * passes it into the method as a parameter. part is then assigned to the
     * variable modifyPart to enhance developer readability. The part fields are
     * then pre-filled out so the user can see the part's current values that
     * they are wanting to modify.
     * @param part
     */
    void setPart(Part part) {

        modifyPart = part;

        modifyPartIdField.setText(Integer.toString(modifyPart.getId()));
        modifyPartNameField.setText(modifyPart.getName());
        modifyPartPriceField.setText(Double.toString(modifyPart.getPrice()));
        modifyPartMaxField.setText(Integer.toString(modifyPart.getMax()));
        modifyPartMinField.setText(Integer.toString(modifyPart.getMin()));
        modifyPartInventoryField.setText(Integer.toString(modifyPart.getStock()));

        if(part instanceof InHouse_Model) {

            modifyPartInHouse.setSelected(true);
            modifyPartOutsourced.setSelected(false);
            InHouse_Model inHouse = (InHouse_Model) part;
            inHouseOutsourcedVarFieldModify.setText("Machine ID");
            modifyPartMachineIdField.setText(Integer.toString(inHouse.getMachineId()));

        } else if(part instanceof Outsourced_Model) {
            modifyPartInHouse.setSelected(false);
            modifyPartOutsourced.setSelected(true);
            Outsourced_Model outsourced = (Outsourced_Model) part;
            inHouseOutsourcedVarFieldModify.setText("Company");
            modifyPartMachineIdField.setText(outsourced.getCompanyName());
        }

    }

    /**
     * The modify_part_controller's inventory variable is set as the inventory that is
     * passed in to this method.
     * @param inv
     */
    public void getPartInventoryModify(Inventory inv) {
        this.inventory = inv;
    }

    /**
     * modifyPartSave validates all user input to ensure correct data types, prevent empty fields, control
     * min, max, and inventory rules (inventory must fall between min and max, min is less than max), and
     * if all validation passes, the selected part is modified with the field values.
     *
     * RUNTIME ERROR - When I created this method, I forgot to include 'throws IOException', which led to
     * a runtime error. This was fixed by adding it.
     * @param actionEvent
     * @throws IOException
     */
    public void modifyPartSave(ActionEvent actionEvent) throws IOException {

        if(modifyPartNameField.getText().trim().isEmpty() || modifyPartInventoryField.getText().trim().isEmpty() || modifyPartMaxField.getText().trim().isEmpty() ||
                modifyPartMinField.getText().trim().isEmpty() || modifyPartPriceField.getText().trim().isEmpty()) {
            errorAlert(1);
        } else {

            if (partFieldTypeValidation(modifyPartInventoryField.getText(), modifyPartPriceField.getText(), modifyPartMinField.getText(), modifyPartMaxField.getText())) {

                Integer partId = Integer.valueOf(modifyPartIdField.getText());
                String partName = String.valueOf(modifyPartNameField.getText());
                Integer partInventory = Integer.valueOf(modifyPartInventoryField.getText());
                Integer partMax = Integer.valueOf(modifyPartMaxField.getText());
                Integer partMin = Integer.valueOf(modifyPartMinField.getText());
                Double partPrice = Double.valueOf(modifyPartPriceField.getText());

                if (modifyPartMachineIdField.getText().trim().isEmpty()) {
                    errorAlert(1);
                } else {
                    if (partMax < partMin) {
                        errorAlert(2);
                    } else {
                        if (partInventory > partMax || partInventory < partMin) {
                            errorAlert(3);
                        } else {

                            if (modifyPartInHouse.isSelected()) {

                                try {
                                    Integer partMachineId = Integer.valueOf(modifyPartMachineIdField.getText());
                                    InHouse_Model modifiedPart = new InHouse_Model(partId, partName, partInventory, partPrice, partMin, partMax, partMachineId);
                                    inventory.updatePart(modifiedPart);

                                    Parent saveButton = FXMLLoader.load(getClass().getResource("/Views/Main_Page.fxml"));
                                    Scene addPartScene = new Scene(saveButton);
                                    Stage addPartStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                    addPartStage.setScene(addPartScene);
                                    addPartStage.show();
                                } catch (Exception e) {
                                    errorAlert(8);
                                }

                            } else if (modifyPartOutsourced.isSelected()) {
                                try {
                                    String partCompany = String.valueOf(modifyPartMachineIdField.getText());
                                    Outsourced_Model modifiedPart = new Outsourced_Model(partId, partName, partInventory, partPrice, partMin, partMax, partCompany);
                                    inventory.updatePart(modifiedPart);

                                    Parent saveButton = FXMLLoader.load(getClass().getResource("/Views/Main_Page.fxml"));
                                    Scene addPartScene = new Scene(saveButton);
                                    Stage addPartStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                    addPartStage.setScene(addPartScene);
                                    addPartStage.show();
                                } catch (Exception e){
                                    errorAlert(1);
                                }
                            }

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
     * cancelButton redirects the user to the main page without saving
     * any user input.
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
     * Sets the text of the inhouse/outsourced user input field appropriately
     * depending on the currently selected radio button, inhouse or outsourced.
     * @param actionEvent
     */
    public void modifyPartInHouse(ActionEvent actionEvent) {
        inHouseOutsourcedVarFieldModify.setText("Machine ID");
    }

    /**
     * Sets the text of the inhouse/outsourced user input field appropriately
     * depending on the currently selected radio button, inhouse or outsourced.
     * @param actionEvent
     */
    public void modifyPartOutsourced(ActionEvent actionEvent) {
        inHouseOutsourcedVarFieldModify.setText("Company");
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
