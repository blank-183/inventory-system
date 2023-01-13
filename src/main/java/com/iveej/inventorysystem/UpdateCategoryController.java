package com.iveej.inventorysystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateCategoryController extends Controller implements Initializable {

    private int categoryID = -1;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tfCategoryName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnCancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnUpdateAction(ActionEvent event) {
        String categoryName = tfCategoryName.getText().trim();

        if (isNoBlankField(categoryName)) {
            return;
        }

        String query = "UPDATE category SET category_name = ? WHERE id = ?";

        Optional<ButtonType> result = confirm(Alert.AlertType.CONFIRMATION, Constant.CONFIRM_MESSAGE,
                "Do you want to update this category?", "Press \"ok\" to confirm.");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try (Connection conn = ConnectDB.getConnection()) {
                assert conn != null;
                try (PreparedStatement stmt = conn.prepareStatement(query)) {

                    stmt.setString(1, categoryName);
                    stmt.setInt(2, categoryID);

                    stmt.execute();

                    showMessage(Alert.AlertType.INFORMATION, Constant.SUCCESS_MESSAGE,
                            "Category successfully updated!",
                            "You can now view the category.");

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void setProductInformation(int id, String name) {
        this.categoryID = id;
        tfCategoryName.setText(name);
    }


}
