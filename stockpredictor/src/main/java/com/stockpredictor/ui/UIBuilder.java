package com.stockpredictor.ui;

import com.stockpredictor.controller.AppController;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class UIBuilder {
    public static Scene build(AppController controller) {
        // Create UI components
        Label label = new Label("Enter Stock Symbol:");
        TextField symbolField = new TextField();
        Button predictButton = new Button("Predict");
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);

        // Button click action: call controller method
        predictButton.setOnAction(e -> controller.onPredictButtonClick(symbolField.getText()));

        // Layout and return the scene
        VBox root = new VBox(10, label, symbolField, predictButton, resultArea);
        return new Scene(root, 400, 300);
    }
}

