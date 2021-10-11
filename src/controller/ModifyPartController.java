package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import utils.Util;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** The controller for the ModifyPart scene. */
public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private  RadioButton outsourcedRadio;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField idInput;

    @FXML
    private TextField invInput;

    @FXML
    private TextField priceInput;

    @FXML
    private TextField maxInput;

    @FXML
    private TextField minInput;

    @FXML
    private Label customLabel;

    @FXML
    private TextField bonusInput;

    private static Part selectedPart = null;

    /** Sets the selected part to be modified. */
    public static void setSelectedPartId(Part part) {
        selectedPart = part;
    }

    /** Takes the selected Part and fills in the inputs. */
    private void setInputs(Part selectedPart) {
        idInput.setText(Integer.toString(selectedPart.getId()));
        nameInput.setText(selectedPart.getName());
        invInput.setText(Integer.toString(selectedPart.getStock()));
        priceInput.setText(Double.toString(selectedPart.getPrice()));
        maxInput.setText(Integer.toString(selectedPart.getMax()));
        minInput.setText(Integer.toString(selectedPart.getMin()));

        InHouse inHousePart = InHouse.getPart(selectedPart.getId());
        Outsourced outsourcedPart = Outsourced.getPart(selectedPart.getId());

        if(inHousePart != null) {
            inHouseRadio.fire();
            bonusInput.setText(Integer.toString(inHousePart.getMachineId()));
        } else {
            outsourcedRadio.fire();
            bonusInput.setText(outsourcedPart.getCompanyName());
        }
    }

    @FXML
    /** Navigate to the home scene. */
    public void goHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        String css = this.getClass().getResource("/view/index.css").toExternalForm();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setTitle("Home");
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    /** Validate and save the current part then navigate the user to the home page. */
    public void savePart(ActionEvent actionEvent) throws IOException {
        String baseErrorMessage = "The Following Errors were found:\n";
        String errorMessages = "";

        if(!Util.isDoubleParsable(priceInput.getText()))
            errorMessages += "Price must be a number\n";
        if(!Util.isIntegerParsable(invInput.getText()))
            errorMessages += "Inventory must be a number\n";
        if(!Util.isIntegerParsable(minInput.getText()))
            errorMessages += "Min must be a number\n";
        if(!Util.isIntegerParsable(maxInput.getText()))
            errorMessages += "Max must be a number\n";
        if(inHouseRadio.isSelected() && !Util.isIntegerParsable(bonusInput.getText()))
            errorMessages += "Machine Id must be a number\n";

        if(errorMessages != "") {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(baseErrorMessage);
            alert.setContentText(errorMessages);

            alert.showAndWait();
            return;
        }

        Double price = Double.parseDouble(priceInput.getText());
        Integer stock = Integer.parseInt(invInput.getText());
        Integer min = Integer.parseInt(minInput.getText());
        Integer max = Integer.parseInt(maxInput.getText());

        if(min > max)
            errorMessages += "Min must be smaller than Max\n";
        if(stock < min || stock > max)
            errorMessages += "Stock must be between Min and Max\n";

        if(errorMessages != "") {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(baseErrorMessage);
            alert.setContentText(errorMessages);

            alert.showAndWait();
            return;
        }

        InHouse.deletePart(selectedPart);
        Outsourced.deletePart(selectedPart);

        if(inHouseRadio.isSelected()) {
            Integer machineId = Integer.parseInt(bonusInput.getText());
            InHouse newPart = new InHouse(selectedPart.getId(), nameInput.getText(), price, stock, min, max, machineId);
            InHouse.addItem(newPart);
        } else {
            String companyName = bonusInput.getText();
            Outsourced newPart = new Outsourced(selectedPart.getId(), nameInput.getText(), price, stock, min, max, companyName);
            Outsourced.addItem(newPart);
        }
        goHome(actionEvent);
    }

    @FXML
    /** Sets the part to be an inHouse part. */
    public void setIsInHouse() {
        customLabel.setText("Machine ID");
    }

    @FXML
    /** Sets the part to be an Outsourced part. */
    public void setIsOutsourced() {
        customLabel.setText("Company Name");
    }

    @Override
    /** Sets the inputs based on selected part. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setInputs(selectedPart);
    }
}
