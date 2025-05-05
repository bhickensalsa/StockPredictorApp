package com.stockpredictor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.stockpredictor.controller.AppController;
import com.stockpredictor.ui.UIBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Main extends Application {

    /**
     * Initializes and displays the main application window.
     * Creates the controller, builds the UI, and sets the scene on the stage.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        // Create the UI components first
        TextField symbolField = new TextField();  // Text field for entering the stock symbol
        TextArea resultArea = new TextArea();  // Area to display prediction results
        resultArea.setEditable(false);  // Make result area non-editable

        // Now create the controller, passing the necessary components
        AppController controller = new AppController(symbolField, resultArea);

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
