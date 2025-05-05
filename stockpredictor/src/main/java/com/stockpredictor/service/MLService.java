package com.stockpredictor.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Service class to interact with the Machine Learning (ML) model via an HTTP API.
 * This class is responsible for sending stock symbol data to the ML service and receiving predictions.
 */
public class MLService {

    /**
     * Fetches the prediction (uptrend or downtrend) for a given stock symbol by calling the ML model API.
     * 
     * @param symbol The stock symbol for which to fetch the prediction.
     * @return The prediction result ("Uptrend" or "Downtrend") returned by the ML model.
     * @throws IOException If an error occurs during the HTTP request or response reading.
     */
    public String fetchPrediction(String symbol) throws IOException {
        // Construct the URL for the ML REST API endpoint
        URL url = new URL("http://localhost:5000/predict");

        // Open an HTTP connection to the specified URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        // Set up the HTTP request properties
        connection.setRequestMethod("POST");  // Use POST method
        connection.setDoOutput(true);  // Allow sending data in the request body
        connection.setRequestProperty("Content-Type", "application/json");  // Set content type to JSON

        // Create JSON payload containing the stock symbol
        String json = "{\"symbol\": \"" + symbol + "\"}";

        // Send the JSON payload to the server
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");  // Convert the string to bytes in UTF-8 encoding
            os.write(input, 0, input.length);  // Write bytes to output stream
        }

        // Read the response from the server
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);  // Append each line of the response
        }
        in.close();  // Close the reader

        // Return the prediction result (e.g., "Uptrend" or "Downtrend")
        return response.toString();
    }
}
