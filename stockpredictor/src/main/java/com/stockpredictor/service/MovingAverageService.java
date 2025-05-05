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
     * @param prices A list of stock prices.
     * @param period The number of prices to consider for the moving average.
     * @return The calculated SMA as a BigDecimal.
     */
    public BigDecimal calculateSMA(List<BigDecimal> prices, int period) {
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
