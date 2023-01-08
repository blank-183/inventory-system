package com.iveej.inventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductsController extends Controller implements Initializable {

    @FXML
    private Label lblUsername;
    @FXML
    private Label lblRole;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> idCol;
    @FXML
    private TableColumn<Product, String> nameCol;
    @FXML
    private TableColumn<Product, String> categoryCol;
    @FXML
    private TableColumn<Product, Integer> quantityCol;
    @FXML
    private TableColumn<Product, Double> orgPriceCol;
    @FXML
    private TableColumn<Product, Double> sellPriceCol;

    ObservableList<Product> productList = FXCollections.observableArrayList();

    Product selectedProduct = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUsername.setText(Controller.getUser().getUsername());
        lblRole.setText(Controller.getUser().getRole());

        loadTable();
    }

    public void btnHomeAction(ActionEvent event) {
        changeScene(event, "home.fxml", "Home");
    }

    public void btnCategoriesAction(ActionEvent event) {

    }

    public void btnNewOrderAction(ActionEvent event) {

    }

    public void btnTransactionsAction(ActionEvent event) {

    }

    public void btnManageUsersAction(ActionEvent event) {

    }

    public void btnLogOutAction(ActionEvent event) {
        logOutUser(event, "login.fxml", "User login");
    }

    public void btnRefreshAction() {
        getProductList();
    }

    private void getProductList() {
        productList.clear();
        String query = "SELECT * FROM product";

        try(Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("product_name");
                String category = getCategory(rs.getInt("category_id"));
                String description = rs.getString("description");
                int quantity = rs.getInt("quantity");
                double originalPrice = rs.getDouble("original_price");
                double sellingPrice = rs.getDouble("selling_price");
                productList.add(new Product(id, name, category, description, quantity, originalPrice, sellingPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orgPriceCol.setCellValueFactory(new PropertyValueFactory<>("orgPrice"));
        sellPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));

        getProductList();
        productsTable.setItems(productList);
    }

    public void btnViewAction(ActionEvent event) throws IOException {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            return;
        }

        System.out.println(selectedProduct.getId() + " " + selectedProduct.getName() + " " + selectedProduct.getDescription());

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("product_details.fxml")));
        Parent root = loader.load();
        ProductDetailsController productDetailsController = loader.getController();
        productDetailsController.setProductInformation(selectedProduct.getName(), selectedProduct.getCategory(),
                                                    selectedProduct.getDescription(), selectedProduct.getQuantity(),
                                                    selectedProduct.getOrgPrice(), selectedProduct.getSellPrice());
        stage.setScene(new Scene(root));
        stage.setTitle(selectedProduct.getName());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

    }

    public void btnAddAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("add_product.fxml")));
        Parent root = loader.load();
        AddProductController addProductController = loader.getController();

        stage.setScene(new Scene(root));
        stage.setTitle("Add Product");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void btnUpdateAction(ActionEvent event) throws IOException {

    }

    public void btnDeleteAction(ActionEvent event) throws IOException {
        selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        String query = "DELETE FROM product WHERE id = ?";

        if(selectedProduct == null) {
            return;
        }

        Optional<ButtonType> result = confirm(Alert.AlertType.CONFIRMATION, Constant.CONFIRM_MESSAGE,
                "You cannot undo this action. Do you really want to delete this product?", "Press \"ok\" to confirm.");
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try(Connection conn = ConnectDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);) {
                stmt.setInt(1, selectedProduct.getId());
                stmt.execute();
                showMessage(Alert.AlertType.INFORMATION, "Success",
                        "Product deleted!", "The product you selected was successfully deleted.");
                getProductList();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
