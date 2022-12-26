package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController extends Controller implements Initializable {


    @FXML
    private Label lblUsername;
    @FXML
    private Label lblRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUsername.setText(Controller.getUser().getUsername());
        lblRole.setText(Controller.getUser().getRole());
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


}
