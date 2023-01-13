package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController extends Controller implements Initializable {

    @FXML
    private Label lblUsername;
    @FXML
    private Label lblFirstName;
    @FXML
    private Label lblRole;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblUsername.setText(Controller.getUser().getUsername());
        lblFirstName.setText(Controller.getUser().getFirstName());
        lblRole.setText(Controller.getUser().getRole());
    }

    public void btnProductsAction(ActionEvent event) {
        changeScene(event, "products.fxml", "View Products");
    }

    public void btnCategoriesAction(ActionEvent event) {
        changeScene(event, "categories.fxml", "View Categories");
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
        logOutUser(event, "login.fxml", "User login");
    }

    public void setUserInformation(User user) {
        lblUsername.setText(user.getUsername());
        if(user.getFirstName().length() > 12)
            lblFirstName.setText(user.getFirstName().substring(0, 16) + ".");
        else
            lblFirstName.setText(user.getFirstName());
        lblRole.setText(user.getRole());
    }


}