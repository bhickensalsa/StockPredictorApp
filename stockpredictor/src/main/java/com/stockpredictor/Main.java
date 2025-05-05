package com.stockpredictor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.stockpredictor.controller.AppController;
import com.stockpredictor.ui.UIBuilder;

/**
 * The main entry point for the Stock Predictor application.
 * This class launches the JavaFX application and sets up the initial UI.
 */
public class Main extends Application {

    /**
     * Initializes and displays the main application window.
     * Creates the controller, builds the UI, and sets the scene on the stage.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        // Create the controller for the application logic
        AppController controller = new AppController();

        // Build the UI using the UIBuilder, passing the controller to link the UI and logic
        Scene scene = UIBuilder.build(controller);

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
