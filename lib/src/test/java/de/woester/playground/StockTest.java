package de.woester.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

class StockTest {

    private static final StockMsg INITIAL_STOCK = new StockMsg(0, 0, 0);
    private static final BuildingCountersMsg INITIAL_BUILDING_COUNTERS = new BuildingCountersMsg(Map.of(
            Building.IRON_MINE, 1L
    ));
    private static final ProductionMsg INITIAL_PRODUCTION = Building.IRON_MINE.getDefinition().production();

    Stock stock;
    Production production;
    Buildings buildings;
    Construction construction;


    @BeforeEach
    void setUp() {
        construction = new Construction(0, new ConstructionMsg(Collections.emptyMap(), Collections.emptySet()));
        buildings = new Buildings(0, INITIAL_BUILDING_COUNTERS, construction);
        production = new Production(buildings);
        stock = new Stock(0, INITIAL_STOCK, production);
    }

    @Test
    void stock_at_tick_zero_is_initial_stock() {
        StockMsg s = stock.getStock(0);
        Assertions.assertEquals(INITIAL_STOCK, s);
    }

    @Test
    void stock_at_tick_one_is_initial_stock_plus_initial_production() {
        StockMsg s = stock.getStock(1);
        Assertions.assertEquals(Stock.sum(INITIAL_STOCK, INITIAL_PRODUCTION), s);
    }

    @Test
    void stock_at_tick_one_hundred() {
        StockMsg s = stock.getStock(100);
        Assertions.assertEquals(100, s.iron());
        Assertions.assertEquals(0, s.energy());
        Assertions.assertEquals(0, s.population());
    }
}