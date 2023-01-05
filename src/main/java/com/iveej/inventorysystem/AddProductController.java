package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private TextField tfProductName;
    @FXML
    private ChoiceBox<String> cbCategory;
    @FXML
    private TextArea taDescription;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TextField tfOrgPrice;
    @FXML
    private TextField tfSellPrice;
    private ArrayList<String> categories = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategories();
        cbCategory.getItems().addAll(categories);
        cbCategory.setValue(categories.get(0));

        tfQuantity.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfQuantity.setText(newValue.replaceAll("\\D", ""));
            }
        });

        tfOrgPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                tfOrgPrice.setText(oldValue);
            }
        });

        tfSellPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                tfSellPrice.setText(oldValue);
            }
        });
    }

    public void btnCloseAction() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void btnAddAction(ActionEvent event) {
        // wait
    }

    public void loadCategories() {
        String query = "SELECT category_name FROM category";

        try(Connection conn = ConnectDB.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

                while(rs.next()) {
                    String category = rs.getString("category_name");
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
