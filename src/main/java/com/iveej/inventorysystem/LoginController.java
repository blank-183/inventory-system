package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Utils {

    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCreateAccount;

    public void btnCreateAccountAction(ActionEvent event) {

    }

    public void btnLoginAction(ActionEvent event) {
        if(tfUsername.getText().isBlank() == false && pfPassword.getText().isBlank() == false) {
            validateLogin();
            System.out.println("Hello");
        } else {
            showMessage(Alert.AlertType.ERROR, "Error", "Please enter username and password!", "All fields are required.");
        }
    }



    private void validateLogin() {

    }

}
