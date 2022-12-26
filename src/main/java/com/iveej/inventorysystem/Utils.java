package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    @FXML
    public void changeScene(ActionEvent event, String fxml, String title, String btn, String username, String firstName, String role) {
        Parent root = null;
        if(btn != null && username != null && role != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxml)));
                root = loader.load();
                if(btn.equals("Home")) {
                    HomeController homeController = loader.getController();
                    homeController.setUserInformation(username, firstName, role);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void showMessage(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    public Optional<ButtonType> confirm(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        return alert.showAndWait();
    }

    public byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public boolean isNoBlankField(String... args) {
        for (String arg : args) {
            if(arg.isBlank()) {
                showMessage(Alert.AlertType.ERROR, Constant.ERROR_MESSAGE,
                        "Missing input!",
                        "Please fill all the required fields.");
                return false;
            }
        }
        return true;
    }

    public boolean isUsernameValid(String username) {
        final String USERNAME_PATTERN = "^[a-zA-Z0-9](_(?!_)|[a-zA-Z0-9]){3,11}[a-zA-Z0-9]$";
        final Pattern pattern = Pattern.compile(USERNAME_PATTERN);

        Matcher matcher = pattern.matcher(username);

        if(matcher.matches()) {
            return true;
        }
        showMessage(Alert.AlertType.ERROR,
                Constant.ERROR_MESSAGE,
                "Username is not valid!",
                "Length must be between 5 and 13. Special characters are not allowed. You can only use underscore (_) but shouldn't start and end with it. Please try another username.");
        return false;
    }
}
