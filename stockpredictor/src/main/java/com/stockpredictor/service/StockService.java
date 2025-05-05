package com.stockpredictor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class StockService {

    // Alpha Vantage
    private static final String ALPHA_VANTAGE_API_KEY = "YOUR_ALPHA_VANTAGE_KEY";
    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Yahoo Finance
    private static final int MAX_RETRIES = 3;
    private static final long BACKOFF_TIME = 2000; // 2 seconds

    /**
     * Retrieves historical stock prices from Yahoo Finance.
     */
    public List<BigDecimal> getYahooHistoricalPrices(String symbol, int days) throws IOException {
        Stock stock = YahooFinance.get(symbol);
        Calendar from = Calendar.getInstance();
        from.add(Calendar.DAY_OF_YEAR, -days);

        List<BigDecimal> closingPrices = new ArrayList<>();
        stock.getHistory(from).forEach(q -> closingPrices.add(q.getClose()));

        return closingPrices;
    }

    /**
     * Retrieves historical stock prices from Alpha Vantage.
     */
    public List<BigDecimal> getAlphaVantageHistoricalPrices(String symbol, int days) throws IOException {
        String requestUrl = ALPHA_VANTAGE_URL + "?function=TIME_SERIES_DAILY&symbol=" + symbol + "&outputsize=compact&apikey=" + ALPHA_VANTAGE_API_KEY;

        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder json = new StringBuilder();
        while (sc.hasNext()) {
            json.append(sc.nextLine());
        }
        sc.close();

        JsonNode root = objectMapper.readTree(json.toString());
        JsonNode timeSeries = root.get("Time Series (Daily)");

        if (timeSeries == null) {
            throw new IOException("Invalid API response from Alpha Vantage: " + root.toString());
        }

        List<BigDecimal> closingPrices = new ArrayList<>();
        Iterator<String> dates = timeSeries.fieldNames();
        while (dates.hasNext() && closingPrices.size() < days) {
            String date = dates.next();
            JsonNode close = timeSeries.get(date).get("4. close");
            if (close != null) {
                closingPrices.add(new BigDecimal(close.asText()));
            }
        }

        return closingPrices;
    }

    /**
     * Get historical stock prices with automatic fallback to Alpha Vantage if Yahoo Finance fails.
     */
    public List<BigDecimal> getHistoricalPrices(String symbol, int days) {
        int retries = 0;

        while (retries < MAX_RETRIES) {
            try {
                // Try fetching from Yahoo Finance first
                return getYahooHistoricalPrices(symbol, days);
            } catch (IOException e) {
                if (e.getMessage().contains("HTTP response code: 429")) {
                    // If rate-limited, retry with backoff
                    retries++;
                    try {
                        System.out.println("Rate limit hit (Yahoo), retrying in " + BACKOFF_TIME * retries + "ms...");
                        Thread.sleep(BACKOFF_TIME * retries);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                } else {
                    // If other error, attempt to fetch from Alpha Vantage
                    try {
                        System.out.println("Switching to Alpha Vantage...");
                        return getAlphaVantageHistoricalPrices(symbol, days);
                    } catch (IOException alphaException) {
                        throw new RuntimeException("Failed to fetch data from both sources: " + alphaException.getMessage(), alphaException);
                    }
                }
            }
        }

        throw new RuntimeException("Exceeded maximum retries. Could not fetch stock data.");
    }
}
