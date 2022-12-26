package com.iveej.inventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
