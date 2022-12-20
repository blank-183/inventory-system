package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
        String query = "SELECT * FROM user WHERE role_id = 1";

        try(Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            if(rs.next()) {
                btnCreateAccount.setDisable(true);
            } else {
                tfUsername.setDisable(true);
                pfPassword.setDisable(true);
                btnLogin.setDisable(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnCreateAccountAction(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("new_admin.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create new admin");
        stage.setResizable(false);
        stage.show();

    }

    public void btnLoginAction(ActionEvent event) {
        if(!tfUsername.getText().isBlank() && !pfPassword.getText().isBlank()) {
            validateLogin();
        } else {
            showMessage(Alert.AlertType.ERROR, "Error",
                    "Please enter username and password!",
                    "All fields are required.");
        }
    }



    private void validateLogin() {

    }

}
