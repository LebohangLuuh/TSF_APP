module com.example.tsf_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires java.desktop;


    opens com.example.tsf_app to javafx.fxml;
    exports com.example.tsf_app;
}