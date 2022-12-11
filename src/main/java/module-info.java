module com.iveej.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.iveej.inventorysystem to javafx.fxml;
    exports com.iveej.inventorysystem;
}