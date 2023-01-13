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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateProductController extends Controller implements Initializable {

    private int productID = -1;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCancel;
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

    private List<String> categories = new ArrayList<>();

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

    public void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnUpdateAction(ActionEvent event) {
        String productName = tfProductName.getText().trim();
        String categoryID = categories.indexOf(cbCategory.getValue()) + 1 + "";
        String description = taDescription.getText();
        String quantity = tfQuantity.getText();
        String orgPrice = tfOrgPrice.getText();
        String sellPrice = tfSellPrice.getText();

        if (isNoBlankField(productName, categoryID, description, quantity, orgPrice, sellPrice)) {
            return;
        }

        String query = "UPDATE product SET product_name = ?, category_id = ?, description = ?, quantity = ?, original_price = ?, selling_price = ? WHERE id = ?";

        Optional<ButtonType> result = confirm(Alert.AlertType.CONFIRMATION, Constant.CONFIRM_MESSAGE,
                "Do you want to update this product?", "Press \"ok\" to confirm.");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = ConnectDB.getConnection()) {
                assert conn != null;
                try (PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, productName);
                    stmt.setInt(2, Integer.parseInt(categoryID));
                    stmt.setString(3, description);
                    stmt.setInt(4, Integer.parseInt(quantity));
                    stmt.setDouble(5, Double.parseDouble(orgPrice));
                    stmt.setDouble(6, Double.parseDouble(sellPrice));
                    stmt.setInt(7, productID);

                    stmt.execute();

                    showMessage(Alert.AlertType.INFORMATION, Constant.SUCCESS_MESSAGE,
                            "Product successfully updated!",
                            "You can now view the product.");

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void setProductInformation(int id, String name, int categoryID, String description, int quantity, Double orgPrice, Double sellPrice) {
        List<String> categories = loadCategories();
        this.productID = id;
        tfProductName.setText(name);
        cbCategory.setValue(categories.get(categoryID - 1));
        taDescription.setText(description);
        tfQuantity.setText(String.valueOf(quantity));
        tfOrgPrice.setText(String.format("%.2f", orgPrice));
        tfSellPrice.setText(String.format("%.2f", sellPrice));
    }


}
