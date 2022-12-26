package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends Utils implements Initializable {

    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnCreateAccount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String query = "SELECT * FROM user";

        try(Connection conn = ConnectDB.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

                if(rs.next()) {
                    btnCreateAccount.setDisable(true);
                } else {
                    tfUsername.setDisable(true);
                    pfPassword.setDisable(true);
                    btnLogin.setDisable(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnCreateAccountAction(ActionEvent event) {
        changeScene(event, "new_admin.fxml", "Create new admin");
    }

    public void btnLoginAction(ActionEvent event) {
        String username = tfUsername.getText().trim();
        String password = pfPassword.getText();

        if(!username.isBlank() && !password.isBlank()) {
            if(!isUsernameValid(username)) { return; }
            validateLogin(event, username, password);
        } else {
            showMessage(Alert.AlertType.ERROR, Constant.ERROR_MESSAGE,
                    "Please enter username and password!",
                    "All fields are required.");
        }
    }

    private void validateLogin(ActionEvent event, String username, String password) {
        String query = "SELECT username, password FROM user WHERE username = ?";

        try {
            password = toHexString(getSHA(pfPassword.getText()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try(Connection conn = ConnectDB.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if(!rs.isBeforeFirst()) {
                    showWrongCredentials();
                    return;
                }

                while(rs.next()) {
                    if(username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
                        showMessage(Alert.AlertType.INFORMATION, Constant.SUCCESS_MESSAGE,
                                "User found!",
                                "You are now logged in.");

                        changeScene(event, "home.fxml", "User Home");
                    } else {
                        showWrongCredentials();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showWrongCredentials() {
        showMessage(Alert.AlertType.ERROR, Constant.ERROR_MESSAGE,
                "Wrong username or password!",
                "Please check your credentials.");
        pfPassword.setText("");
    }

}
