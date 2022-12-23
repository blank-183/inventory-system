package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class NewAdminController extends Utils {

    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private PasswordField pfConfirmPassword;

    public void btnCreateAction(ActionEvent event) {
        String firstName = tfFirstName.getText().trim();
        String lastName = tfLastName.getText().trim();
        String username = tfUsername.getText().trim();
        String password = pfPassword.getText();
        String confirmPassword = pfConfirmPassword.getText();

        if(!isNoBlankField(firstName, lastName, username, password, confirmPassword)) { return; }
        if(!isValidLength(firstName, lastName, username)) { return; }
        if(!isUsernameValid(username)) { return; }
        if(!isPasswordStrong(password)) { return; }

        if(password.equals(confirmPassword)) {
            Optional<ButtonType> result = confirm(Alert.AlertType.CONFIRMATION, Constant.CONFIRM_MESSAGE,
                    "Do you want to create new admin?", "Press \"ok\" to confirm.");
            if(result.isPresent() && result.get() == ButtonType.OK) {
                createAdmin(event, firstName, lastName, username, password);
            }

        } else {
            showMessage(Alert.AlertType.ERROR, Constant.ERROR_MESSAGE,
                    "Passwords do not match!",
                    "Please double-check the passwords you entered.");
        }

    }

    public void btnGoBackAction(ActionEvent event) {
        changeScene(event, "login.fxml", "User login");
    }

    private void createAdmin(ActionEvent event, String firstName, String lastName, String username, String password)  {
        String query = "INSERT INTO user (first_name, last_name, username, password, role_id) VALUES (?, ?, ?, ?, 1)";

        try {
            password = toHexString(getSHA(pfPassword.getText()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try(Connection conn = ConnectDB.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, username);
                stmt.setString(4, password);

                stmt.execute();

                showMessage(Alert.AlertType.INFORMATION, Constant.SUCCESS_MESSAGE,
                        "Admin successfully created!",
                        "You can now log in.");

                changeScene(event, "login.fxml", "User login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean isValidLength(String firstName, String lastName, String username) {
        if(firstName.length() > 32) {
            showMessage(Alert.AlertType.ERROR, Constant.ERROR_MESSAGE,
                    "First name is too long!",
                    "Please limit your first name to 32 characters.");
            return false;
        } else if(lastName.length() > 32) {
            showMessage(Alert.AlertType.ERROR, Constant.ERROR_MESSAGE,
                    "Last name is too long!",
                    "Please limit your last name to 32 characters.");
            return false;
        } else if(username.length() > 32) {
            showMessage(Alert.AlertType.ERROR, Constant.ERROR_MESSAGE,
                    "Username is too long!",
                    "Please limit your username to 32 characters.");
            return false;
        }

        return true;
    }

    private boolean isUsernameUnique(String username) {
        String query = "SELECT * FROM user WHERE username = ?";

        try(Connection conn = ConnectDB.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if(rs.next()) {
                    showMessage(Alert.AlertType.ERROR,
                            Constant.ERROR_MESSAGE,
                            "Username is already taken!",
                            "Please try different username.");
                    return false;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean isPasswordStrong(String password) {
        if(password.length() >= 8) {
            return true;
        }

        showMessage(Alert.AlertType.ERROR,
                Constant.ERROR_MESSAGE,
                "Passwords is weak!",
                "The minimum password length is 8.");
        return false;
    }

}
