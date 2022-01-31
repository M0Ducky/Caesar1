module main.caesar {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.caesar to javafx.fxml;
    exports main.caesar;
}