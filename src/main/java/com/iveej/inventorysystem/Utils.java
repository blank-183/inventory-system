package com.iveej.inventorysystem;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class Utils {
    @FXML
    public void showMessage(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}