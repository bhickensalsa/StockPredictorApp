package com.stockpredictor.service;

import java.math.BigDecimal;
import java.util.List;

public class MovingAverageService {
    public BigDecimal calculateSMA(List<BigDecimal> prices, int period) {
        // Get the last 'period' number of prices and calculate the average
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = prices.size() - period; i < prices.size(); i++) {
            sum = sum.add(prices.get(i));
        }
        return sum.divide(BigDecimal.valueOf(period), BigDecimal.ROUND_HALF_UP);
    }
}
