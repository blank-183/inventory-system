package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private Button btnAdd;

    @FXML
    private ChoiceBox<String> cbCategory;
    private ArrayList<String> categories = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategories();
        cbCategory.getItems().addAll(categories);
        cbCategory.setValue(categories.get(0));
    }

    public void btnCloseAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void btnAddAction(ActionEvent event) {

    }

    public void loadCategories() {
        String query = "SELECT category_name FROM category";

        try(Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while(rs.next()) {
                String category = rs.getString("category_name");
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
