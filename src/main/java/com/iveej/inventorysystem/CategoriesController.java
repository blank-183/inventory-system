package com.iveej.inventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

public class CategoriesController extends Controller implements Initializable {

    @FXML
    private Label lblUsername;
    @FXML
    private Label lblRole;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableView<Category> categoriesTable;
    @FXML
    private TableColumn<Category, Integer> idCol;
    @FXML
    private TableColumn<Category, String> nameCol;

    ObservableList<Category> categoryList = FXCollections.observableArrayList();
    Category selectedCategory = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUsername.setText(Controller.getUser().getUsername());
        lblRole.setText(Controller.getUser().getRole());
        loadTable();
        FilteredList<Category> filteredData = new FilteredList<>(categoryList, p -> true);

        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(category -> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            return String.valueOf(category.getName()).toLowerCase().contains(lowerCaseFilter);
        }));

        SortedList<Category> sortedList = new SortedList<>(filteredData);

        sortedList.comparatorProperty().bind(categoriesTable.comparatorProperty());
        categoriesTable.setItems(sortedList);
    }

    public void btnHomeAction(ActionEvent event) {
        changeScene(event, "home.fxml", "Home");
    }

    public void btnProductsAction(ActionEvent event) {
        changeScene(event, "products.fxml", "View Products");
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
        getCategoryList();
    }

    private void getCategoryList() {
        categoryList.clear();
        String query = "SELECT * FROM category";

        try(Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("category_name");
                categoryList.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        getCategoryList();
        categoriesTable.setItems(categoryList);
    }

    public void btnAddAction() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("add_category.fxml")));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Category");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        getCategoryList();
    }

    public void btnUpdateAction() throws IOException {
        selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();

        if(selectedCategory == null) return;

        System.out.println(selectedCategory.getId() + " " + selectedCategory.getName());

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("update_category.fxml")));
        Parent root = loader.load();
        UpdateCategoryController updateCategoryController = loader.getController();
        updateCategoryController.setProductInformation(selectedCategory.getId(), selectedCategory.getName());
        stage.setScene(new Scene(root));
        stage.setTitle("Update Category: " + selectedCategory.getName());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        getCategoryList();
    }

    public void btnDeleteAction() {
        selectedCategory = categoriesTable.getSelectionModel().getSelectedItem();
        String query = "DELETE FROM category WHERE id = ?";

        if(selectedCategory == null) return;

        Optional<ButtonType> result = confirm(Alert.AlertType.CONFIRMATION, Constant.CONFIRM_MESSAGE,
                "You cannot undo this action. Do you really want to delete this category?", "Press \"ok\" to confirm.");
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try(Connection conn = ConnectDB.getConnection()) {
                assert conn != null;
                try(PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, selectedCategory.getId());
                    stmt.execute();
                    showMessage(Alert.AlertType.INFORMATION, "Success",
                            "Category deleted!", "The category you selected was successfully deleted.");
                    getCategoryList();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                showMessage(Alert.AlertType.ERROR, Constant.ERROR_MESSAGE,
                        "Cannot delete category!",
                        "The category might be in used by another table.");
            }
        }
    }
}
