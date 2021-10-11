package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Util;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** The controller for the Home scene. */
public class HomeController implements Initializable {

    @FXML
    public TextField partsSearch;

    @FXML
    public TableView partsTable;

    @FXML
    public TableColumn colPartId;

    @FXML
    public TableColumn colPartName;

    @FXML
    public TableColumn colPartLevel;

    @FXML
    public TableColumn colPartPrice;

    @FXML
    public Button partsAdd;

    @FXML
    public Button partsModify;

    @FXML
    public Button partsDelete;

    @FXML
    public TextField productsSearch;

    @FXML
    public TableView productsTable;

    @FXML
    public TableColumn colProdId;

    @FXML
    public TableColumn colProdName;

    @FXML
    public TableColumn colProdLevel;

    @FXML
    public TableColumn colProdCost;

    @FXML
    public Button productAdd;

    @FXML
    public Button productModify;

    @FXML
    public Button productDelete;

    @FXML
    /** Opens the modify part scene and passes in the selected part. */
    void modifyPart(ActionEvent actionEvent) throws IOException {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected");
            alert.setHeaderText(null);
            alert.setContentText("No Part selected for deletion");

            alert.showAndWait();
            return;
        }

        ModifyPartController.setSelectedPartId(selectedPart);

        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyPart.fxml"));
        String css = this.getClass().getResource("/view/index.css").toExternalForm();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setTitle("Modify Part");
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    /** Opens the modify Product scene and passes in the selected Product. */
    void modifyProduct(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Product Selected");
            alert.setHeaderText(null);
            alert.setContentText("No Product selected for deletion");

            alert.showAndWait();
            return;
        }

        ModifyProductController.setSelectedProduct(selectedProduct);

        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProduct.fxml"));
        String css = this.getClass().getResource("/view/index.css").toExternalForm();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setTitle("Modify Product");
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    /** Takes user Input and filters parts out of the Parts Table. */
    public void filterParts(String searchTerm) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = AllParts.getAllParts();

        if(searchTerm != "") {
            boolean noPartsFound = true;
            for(Part part : allParts) {
                if(part.getName().contains(searchTerm)) {
                    noPartsFound = false;
                    matchingParts.add(part);
                } else if(Util.isIntegerParsable(searchTerm) && part.getId() == Integer.parseInt(searchTerm)) {
                    noPartsFound = false;
                    matchingParts.add(part);
                }
            }
            if(noPartsFound) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Parts Found");
                alert.setHeaderText(null);
                alert.setContentText("No Parts were found with that given search term.");

                alert.showAndWait();
            }
            partsTable.setItems(matchingParts);
            return;
        }
        partsTable.setItems(allParts);

    }

    @FXML
    /** Takes user input and filters out of the Products table. */
    public void filterProducts(String searchTerm) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Product.getAllProducts();

        if(searchTerm != "") {
            boolean noProductsFound = true;
            for(Product product : allProducts) {
                if(product.getName().contains(searchTerm)) {
                    noProductsFound = false;
                    matchingProducts.add(product);
                } else if(Util.isIntegerParsable(searchTerm) && product.getId() == Integer.parseInt(searchTerm)) {
                    noProductsFound = false;
                    matchingProducts.add(product);
                }
            }
            if(noProductsFound) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Products Found");
                alert.setHeaderText(null);
                alert.setContentText("No Products were found with that given search term.");

                alert.showAndWait();
            }
            productsTable.setItems(matchingProducts);
            return;
        }
        productsTable.setItems(allProducts);
    }

    @FXML
    /** Navigates to the addPart scene */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        String css = this.getClass().getResource("/view/index.css").toExternalForm();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setTitle("Add Part");
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    /** Navigates to the addProduct scene */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        String css = this.getClass().getResource("/view/index.css").toExternalForm();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setTitle("Add Product");
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    /** Tries to delete the selected Part. */
    public void deletePart(ActionEvent actionEvent) {
        /** FUTURE ENHANCEMENT
         *  It would be a good idea to check that a given part is not included
         *  in any product before it is deleted. A check could be made before
         *  deleting it and if it is used prevent the user from deleting it.
         * */

        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Part Selected");
            alert.setHeaderText(null);
            alert.setContentText("No Part selected for deletion");

            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this Part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Outsourced.deletePart(selectedPart);
            InHouse.deletePart(selectedPart);

            setTables();
        }
    }

    @FXML
    /** Tries to delete the selected Product. */
    public void deleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Product Selected");
            alert.setHeaderText(null);
            alert.setContentText("No Product selected for deletion");

            alert.showAndWait();
            return;
        }

        if(selectedProduct.getAllAssociatedParts().size() != 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Product still has parts");
            alert.setHeaderText(null);
            alert.setContentText("You must remove all associated parts before you can delete a Product.");

            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this Product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Product.deleteProduct(selectedProduct);
            setTables();
        }
    }

    /** Sets the values of the Parts and Products Tables. */
    public void setTables() {
        AllParts.gatherAllParts();

        partsTable.setItems(AllParts.getAllParts());
        colPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPartLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productsTable.setItems(Product.getAllProducts());
        colProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colProdCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProdLevel.setCellValueFactory(new PropertyValueFactory<>("Stock"));
    }

    @Override
    /** Sets up values for the homeView */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterParts(newValue);
        });

        productsSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filterProducts(newValue);
        });

        setTables();
    }
}