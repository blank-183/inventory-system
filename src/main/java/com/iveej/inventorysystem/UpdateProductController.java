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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateProductController extends Controller implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnUpdateAction(ActionEvent event) {
    }

    public void setProductInformation(String name, int categoryID, String description, Integer quantity, Double orgPrice, Double sellPrice) {
        ArrayList<String> categories = loadCategories();
        tfProductName.setText(name);
        cbCategory.setValue(categories.get(categoryID - 1));
        taDescription.setText(description);
        tfQuantity.setText(String.valueOf(quantity));
        tfOrgPrice.setText(String.format("%,.2f", orgPrice));
        tfSellPrice.setText(String.format("%,.2f", sellPrice));
    }


}
