package com.stockpredictor.controller;

import com.stockpredictor.service.MLService;
import com.stockpredictor.service.MovingAverageService;
import com.stockpredictor.service.StockService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller for the stock prediction application.
 * Handles user input, communicates with services, and updates the UI.
 */
public class AppController {

    private final StockService stockService = new StockService();
    private final MovingAverageService maService = new MovingAverageService();
    private final MLService mlService = new MLService();

    /**
     * Text field for user to input the stock symbol.
     * Must be linked to the corresponding fx:id in the FXML.
     */
    @FXML
    private TextField symbolInput;

    /**
     * Text area for displaying prediction results.
     * Must be linked to the corresponding fx:id in the FXML.
     */
    @FXML
    private TextArea resultArea;

    /**
     * Initializes the controller.
     * This method is automatically called after the FXML file is loaded.
     * Sets a default prompt in the result area.
     */
    @FXML
    public void initialize() {
        resultArea.setText("Enter a stock symbol and click Predict.");
    }

    /**
     * Called when the "Predict" button is clicked in the UI.
     * Retrieves the stock symbol input, fetches historical data, computes moving average,
     * calls the ML service for prediction, and displays the result.
     *
     * @param string The stock symbol entered by the user.
     */
    @FXML
    public void onPredictButtonClick(String string) {
        String symbol = string.trim();

        if (symbol.isEmpty()) {
            resultArea.setText("Please enter a stock symbol.");
            return;
        }

        try {
            // Retrieve the last 250 days of historical stock prices
            List<BigDecimal> prices = stockService.getHistoricalPrices(symbol, 250);

            // Calculate the 200-day simple moving average (MA200)
            BigDecimal ma200 = maService.calculateSMA(prices, 200);

            // Get the machine learning-based prediction for the stock
            String prediction = mlService.fetchPrediction(symbol);

            // Display the results in the UI
            resultArea.setText("MA200: " + ma200 + "\nPrediction: " + prediction);
        } catch (Exception e) {
            // Display error message in case of failure
            resultArea.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
