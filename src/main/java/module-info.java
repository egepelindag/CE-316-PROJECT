module com.example.ce316project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens com.example.ce316project to javafx.fxml;
    exports com.example.ce316project;
}