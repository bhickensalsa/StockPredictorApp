package com.stockpredictor.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class StockService {
    public List<BigDecimal> getHistoricalPrices(String symbol, int days) throws IOException {
        Stock stock = YahooFinance.get(symbol);
        Calendar from = Calendar.getInstance();
        from.add(Calendar.DAY_OF_YEAR, -days);

        List<BigDecimal> closingPrices = new ArrayList<>();
        stock.getHistory(from).forEach(q -> closingPrices.add(q.getClose()));
        return closingPrices;
    }
}
