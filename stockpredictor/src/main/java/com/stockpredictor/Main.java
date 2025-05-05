package com.stockpredictor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.stockpredictor.ui.UIBuilder;

public class Main extends Application {

    /**
     * Initializes and displays the main application window.
     * Creates the controller, builds the UI, and sets the scene on the stage.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        // Build the UI using the UIBuilder (no need to pass controller anymore)
        Scene scene = UIBuilder.build();

        // Set the scene on the stage and configure the window properties
        stage.setScene(scene);
        stage.setTitle("Stock Predictor");  // Set window title
        stage.show();  // Show the main application window
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
