package com.iveej.inventorysystem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ProductDetailsController extends Controller {

    @FXML
    private Label lblName;
    @FXML
    private Label lblCategory;
    @FXML
    private TextArea textDescription;
    @FXML
    private Label lblQuantity;
    @FXML
    private Label lblOrgPrice;
    @FXML
    private Label lblSellPrice;
    @FXML
    private Button btnClose;

    @FXML
    public void btnCloseAction() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void setProductInformation(String name, int category, String description, Integer quantity, Double orgPrice, Double sellPrice) {
        lblName.setText(name);
        lblCategory.setText(getCategory(category));
        textDescription.setText(description);
        if(quantity <= 1) {
            lblQuantity.setText(quantity + " pc");
        } else {
            lblQuantity.setText(quantity + " pcs");
        }

        lblOrgPrice.setText("P" + String.format("%,.2f", orgPrice));
        lblSellPrice.setText("P" + String.format("%,.2f", sellPrice));
    }

}
