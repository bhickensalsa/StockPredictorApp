package com.stockpredictor.controller;

import com.stockpredictor.service.MLService;
import com.stockpredictor.service.MovingAverageService;
import com.stockpredictor.service.StockService;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
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

    // UI components
    private TextField symbolInput;
    private TextArea resultArea;

    /**
     * This constructor takes in the UI components and links them to the controller.
     */
    public AppController(TextField symbolInput, TextArea resultArea) {
        this.symbolInput = symbolInput;
        this.resultArea = resultArea;
    }

    /**
     * Initializes the controller.
     * Sets a default prompt in the result area.
     */
    public void initialize() {
        if (resultArea != null) {
            resultArea.setText("Enter a stock symbol and click Predict.");
        } else {
            System.out.println("resultArea is not initialized!");
        }
    }

    /**
     * Called when the "Predict" button is clicked in the UI.
     * Retrieves the stock symbol input, fetches historical data, computes moving average,
     * calls the ML service for prediction, and displays the result.
     *
     * @param string The stock symbol entered by the user.
     */
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
        } catch (IOException e) {
            // Display error message in case of failure to fetch stock data
            resultArea.setText("Error: Could not fetch stock data for symbol: " + symbol + "\n" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Display error message for any other issues
            resultArea.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
