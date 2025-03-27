package de.woester.playground;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Stock {

    private final NavigableMap<Long, StockMsg> history = new TreeMap<>();
    private final Production production;

    public Stock(long tick, StockMsg stockAtTick, Production production) {
        this.production = production;
        history.put(tick, stockAtTick);
    }

    public StockMsg getStock(long tick) {
        if (tick < 0) {
            throw new IllegalArgumentException("tick must be a positive number");
        }
        Map.Entry<Long, StockMsg> lastKnown = history.floorEntry(tick);
        StockMsg result = lastKnown.getValue();
        for (long i = lastKnown.getKey() + 1; i <= tick; i++) {
            ProductionMsg p = this.production.getProduction(i);
            result = sum(result, p);
            history.put(i, result);
        }

        return result;
    }

    public static StockMsg sum(StockMsg stock, ProductionMsg production) {
        // stocks can never be negative
        return new StockMsg(
                Math.max(0, stock.iron() + production.iron()),
                Math.max(0, stock.energy() + production.energy()),
                Math.max(0, stock.population() + production.population())
        );
    }
}
