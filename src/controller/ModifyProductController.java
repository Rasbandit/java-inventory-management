package controller;

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
import model.AllParts;
import model.Part;
import model.Product;
import utils.Util;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** The controller for the ModifyProduct scene. */
public class ModifyProductController implements Initializable {

    @FXML
    private TableView<Part> allPartsTable;

    @FXML
    private TableColumn colIdAll;

    @FXML
    private TableColumn colNameAll;

    @FXML
    private TableColumn colLvlAll;

    @FXML
    private TableColumn colPriceAll;

    @FXML
    private TextField searchAllParts;

    @FXML
    private TableView<Part> selectedPartsTable;

    @FXML
    private TableColumn colIdSelected;

    @FXML
    private TableColumn colNameSelected;

    @FXML
    private TableColumn colLvlSelected;

    @FXML
    private TableColumn colPriceSelected;

    @FXML
    private TextField idInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField invInput;

    @FXML
    private TextField priceInput;

    @FXML
    private TextField maxInput;

    @FXML
    private TextField minInput;

    static Product selectedProduct = null;
    ObservableList<Part> allParts = AllParts.getAllParts();
    ObservableList<Part> selectedParts = FXCollections.observableArrayList();

    /** Set the selected product */
    public static void setSelectedProduct(Product product) {
        selectedProduct = product;
    }

    /** Set the values of the selectedParts and AllParts tables. */
    public void setTables() {
        AllParts.gatherAllParts();

        allPartsTable.setItems(allParts);
        colIdAll.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNameAll.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLvlAll.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPriceAll.setCellValueFactory(new PropertyValueFactory<>("price"));

        selectedPartsTable.setItems(selectedParts);
        colIdSelected.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNameSelected.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLvlSelected.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPriceSelected.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    @FXML
    /** Add the selected part to the product. */
    public void addPart() {
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null)
            return;

        selectedParts.add(selectedPart);
    }

    @FXML
    /** Remove the selected part from the product. */
    public void removePart() {
        Part selectedPart = (Part) selectedPartsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove this part from the product.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            selectedParts.remove(selectedPart);
        }
    }

    @FXML
    /** Validate and Update the Product then navigate to the home screen. */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
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

        Product.deleteProduct(selectedProduct);

        Product newProduct = new Product(selectedProduct.getId(), nameInput.getText(), price, stock, min, max, selectedParts);
        Product.addProduct(newProduct);

        goHome(actionEvent);
    }

    @FXML
    /** Navigate to the home screen. */
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
    /** Filters the parts in the allParts table based on user input. */
    public void filterParts(String searchTerm) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();

        if(searchTerm != "") {
            for(Part part : allParts) {
                if(part.getName().contains(searchTerm)) {
                    matchingParts.add(part);
                } else if(Util.isIntegerParsable(searchTerm) && part.getId() == Integer.parseInt(searchTerm)) {
                    matchingParts.add(part);
                } else {
                    //todo: add alert
                }
            }
            allPartsTable.setItems(matchingParts);
            return;
        }
        allPartsTable.setItems(allParts);
    }

    /** Sets the inputs values based on the selected product. */
    private void setInputs(Product selectedProduct) {
        idInput.setText(Integer.toString(selectedProduct.getId()));
        nameInput.setText(selectedProduct.getName());
        invInput.setText(Integer.toString(selectedProduct.getStock()));
        priceInput.setText(Double.toString(selectedProduct.getPrice()));
        maxInput.setText(Integer.toString(selectedProduct.getMax()));
        minInput.setText(Integer.toString(selectedProduct.getMin()));
    }

    @Override
    /** Initialize and setup the ModifyProduct scene to show correct values. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchAllParts.textProperty().addListener((observable, oldValue, newValue) -> {
            filterParts(newValue);
        });

        allParts = AllParts.getAllParts();
        selectedParts = selectedProduct.getAllAssociatedParts();

        setTables();
        setInputs(selectedProduct);
    }

}
