package com.stockpredictor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.stockpredictor.controller.AppController;
import com.stockpredictor.ui.UIBuilder;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        AppController controller = new AppController();
        Scene scene = UIBuilder.build(controller);

        stage.setScene(scene);
        stage.setTitle("Stock Predictor");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
