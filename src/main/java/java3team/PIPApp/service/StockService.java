package service;

import api.model.FinnhubApiClient;
import api.model.StockQuote;

public class StockService {

    private final FinnhubApiClient finnhubApiClient;

    public StockService() {
        this.finnhubApiClient = new FinnhubApiClient();
    }

    public StockQuote getLiveStockQuote(String symbol) {
        return finnhubApiClient.getStockQuote(symbol).orElse(null);
    }
}
