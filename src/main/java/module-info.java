module salon {
    requires javafx.controls;
    requires javafx.fxml;

    opens salon to javafx.fxml;
    exports salon;
}
