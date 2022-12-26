package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends Utils implements Initializable {

    @FXML
    private Label lblUsername;
    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnProductsAction(ActionEvent event) {

    }

    public void btnCategoriesAction(ActionEvent event) {

    }

    public void btnNewOrderAction(ActionEvent event) {

    }

    public void btnTransactionsAction(ActionEvent event) {

    }

    public void btnManageUsersAction(ActionEvent event) {

    }

    public void btnHNewOrderAction(ActionEvent event) {

    }

    public void btnAddProductAction(ActionEvent event) {

    }

    public void btnLogOutAction(ActionEvent event) {

    }

    public void setUserInformation(String username, String firstName, String role) {
        lblUsername.setText(username);
        if(firstName.length() > 12)
            lblFirstName.setText(firstName.substring(0, 16) + ".");
        else
            lblFirstName.setText(firstName);
        lblRole.setText(role);
    }


}