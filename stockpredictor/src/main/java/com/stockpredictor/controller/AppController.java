package com.stockpredictor.controller;

import com.stockpredictor.service.MLService;
import com.stockpredictor.service.MovingAverageService;
import com.stockpredictor.service.StockService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.util.List;

public class AppController {

    private final StockService stockService = new StockService();
    private final MovingAverageService maService = new MovingAverageService();
    private final MLService mlService = new MLService();

    // FXML fields must be annotated and match fx:id in the FXML file
    @FXML
    private TextField symbolInput;

    @FXML
    private TextArea resultArea;

    // Called automatically after FXML is loaded
    @FXML
    public void initialize() {
        resultArea.setText("Enter a stock symbol and click Predict.");
    }

    // This method is called by an FXML action (e.g., Button onAction)
    @FXML
    public void onPredictButtonClick(String string) {
        String symbol = string.trim();

        if (symbol.isEmpty()) {
            resultArea.setText("Please enter a stock symbol.");
            return;
        }

        try {
            // Fetch stock prices
            List<BigDecimal> prices = stockService.getHistoricalPrices(symbol, 250);

            // Calculate MA200
            BigDecimal ma200 = maService.calculateSMA(prices, 200);

            // Get ML prediction
            String prediction = mlService.fetchPrediction(symbol);

            // Display the result
            resultArea.setText("MA200: " + ma200 + "\nPrediction: " + prediction);
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
