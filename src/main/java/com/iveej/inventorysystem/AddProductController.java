package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class AddProductController extends Controller implements Initializable {

    @FXML
    private Button btnCancel;
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
        categories  = loadCategories();
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

    public void btnCancelAction() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnAddAction(ActionEvent event) {
        String productName = tfProductName.getText().trim();
        Integer categoryID = Integer.parseInt(String.valueOf(categories.indexOf(cbCategory.getValue()))) + 1;
        String description = taDescription.getText();
        Integer quantity = Integer.parseInt(tfQuantity.getText());
        Double orgPrice = Double.parseDouble(tfOrgPrice.getText());
        Double sellPrice = Double.parseDouble(tfSellPrice.getText());

        if(!isNoBlankField(productName, categoryID.toString(), description, quantity.toString(), orgPrice.toString(), sellPrice.toString())) { return; }

        String query = "INSERT INTO product (product_name, category_id, description, quantity, original_price, selling_price) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productName);
            stmt.setInt(2, categoryID);
            stmt.setString(3, description);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, orgPrice);
            stmt.setDouble(6, sellPrice);

            stmt.execute();

            showMessage(Alert.AlertType.INFORMATION, Constant.SUCCESS_MESSAGE,
                    "Product successfully added!",
                    "You can now view the product.");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
