package com.example;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MaGoController controller = new MaGoController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}