package com.example.tsf_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TSF_Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TSF_Application.class.getResource("/com/example/tsf_app/TSF_APP_LOGIN_PAGE.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 335, 600);
        stage.setTitle("REGISTRATION");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}