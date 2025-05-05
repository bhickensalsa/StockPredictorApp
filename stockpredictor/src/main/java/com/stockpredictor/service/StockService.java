package com.stockpredictor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

/**
 * Service class for retrieving stock price data.
 * This class uses the Yahoo Finance API to fetch historical stock prices.
 */
public class StockService {

    /**
     * Retrieves historical closing prices for a given stock symbol.
     * 
     * @param symbol The stock symbol (e.g., "AAPL").
     * @param days The number of days of historical data to retrieve.
     * @return A list of closing prices for the specified stock over the last 'days' number of days.
     * @throws IOException If there is an error fetching data from Yahoo Finance.
     */
    public List<BigDecimal> getHistoricalPrices(String symbol, int days) throws IOException {
        // Fetch the stock data for the given symbol using Yahoo Finance API
        Stock stock = YahooFinance.get(symbol);
        
        // Get the current date and subtract 'days' to get the start date for historical data
        Calendar from = Calendar.getInstance();
        from.add(Calendar.DAY_OF_YEAR, -days);

        // List to store the closing prices
        List<BigDecimal> closingPrices = new ArrayList<>();
        
        // Retrieve historical data and add the closing prices to the list
        stock.getHistory(from).forEach(q -> closingPrices.add(q.getClose()));

        // Return the list of closing prices
        return closingPrices;
    }
}
