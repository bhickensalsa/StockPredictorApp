package com.stockpredictor.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Service class to calculate moving averages for stock prices.
 * This class provides a method to calculate the Simple Moving Average (SMA) over a specified period.
 */
public class MovingAverageService {

    /**
     * Calculates the Simple Moving Average (SMA) for a given list of stock prices and period.
     * 
     * @param prices A list of stock prices. Must be non-empty and contain at least 'period' elements.
     * @param period The number of prices to consider for the moving average. Must be less than or equal to the size of 'prices'.
     * @return The calculated SMA as a BigDecimal, or BigDecimal.ZERO if the input is invalid.
     * @throws IllegalArgumentException if prices is empty or the period is greater than the size of the prices list.
     */
    public BigDecimal calculateSMA(List<BigDecimal> prices, int period) {
        // Validate input parameters
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("Prices list cannot be empty.");
        }
        if (period <= 0) {
            throw new IllegalArgumentException("Period must be greater than zero.");
        }
        if (prices.size() < period) {
            throw new IllegalArgumentException("Period cannot be greater than the size of the prices list.");
        }

        // Initialize sum as zero to accumulate the values
        BigDecimal sum = BigDecimal.ZERO;
        
        // Loop through the last 'period' number of prices
        for (int i = prices.size() - period; i < prices.size(); i++) {
            sum = sum.add(prices.get(i));  // Add each price to the sum
        }
        
        // Calculate the average by dividing the sum by the period
        // Using the new method with scale and RoundingMode
        return sum.divide(BigDecimal.valueOf(period), 2, RoundingMode.HALF_UP);
    }
}
