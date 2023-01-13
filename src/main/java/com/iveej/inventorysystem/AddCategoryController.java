package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class AddCategoryController extends Controller {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfCategoryName;

    public void btnCancelAction() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnAddAction(ActionEvent event) {
        String categoryName = tfCategoryName.getText().trim();

        if(isNoBlankField(categoryName)) { return; }

        String query = "INSERT INTO category (category_name) VALUES (?)";

        Optional<ButtonType> result = confirm(Alert.AlertType.CONFIRMATION, Constant.CONFIRM_MESSAGE,
                "Do you want to add this new category?", "Press \"ok\" to confirm.");
        if(result.isPresent() && result.get() == ButtonType.OK) {
            try(Connection conn = ConnectDB.getConnection()) {
                assert conn != null;
                try(PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, categoryName);
                    stmt.execute();

                    showMessage(Alert.AlertType.INFORMATION, Constant.SUCCESS_MESSAGE,
                            "Category successfully added!",
                            "You can now view the category.");

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
