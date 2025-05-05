package com.stockpredictor.ui;

import com.stockpredictor.controller.AppController;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Utility class for building the user interface (UI) of the stock predictor application.
 * This class is responsible for constructing the scene and linking UI elements to the controller.
 */
public class UIBuilder {

    /**
     * Builds and returns the scene for the application, including all necessary UI components.
     * 
     * @param controller The controller instance to link with UI components (e.g., button actions).
     * @return A Scene object containing the constructed UI layout.
     */
    public static Scene build(AppController controller) {
        // Create the UI components
        Label label = new Label("Enter Stock Symbol:");  // Label for user input prompt
        TextField symbolField = new TextField();  // Text field for entering the stock symbol
        Button predictButton = new Button("Predict");  // Button to trigger prediction action
        TextArea resultArea = new TextArea();  // Area to display prediction results
        resultArea.setEditable(false);  // Make result area non-editable

        // Set up the button click action: When clicked, call the controller method
        predictButton.setOnAction(e -> controller.onPredictButtonClick(symbolField.getText()));

        // Pass the UI components to the controller through the constructor
        AppController appController = new AppController(symbolField, resultArea);

        // Create the layout using VBox, arranging UI elements vertically with 10px spacing
        VBox root = new VBox(10, label, symbolField, predictButton, resultArea);

        // Return the scene with the specified layout and size
        return new Scene(root, 400, 300);
    }
}
