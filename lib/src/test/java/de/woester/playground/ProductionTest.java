package de.woester.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

class ProductionTest {

    private static final BuildingCountersMsg INITIAL_BUILDINGS = new BuildingCountersMsg(Map.of(
            Building.IRON_MINE, 1L
    ));
    private static final ProductionMsg INITIAL_PRODUCTION = Building.IRON_MINE.getDefinition().production();

    Production production;
    Buildings buildings;
    Construction construction;

    @BeforeEach
    void setUp() {
        construction = new Construction(0, new ConstructionMsg(Collections.emptyMap(), Collections.emptySet()));
        buildings = new Buildings(0, INITIAL_BUILDINGS, construction);
        production = new Production(buildings);
    }

    @Test
    void production_at_tick_zero_is_initial_production() {
        ProductionMsg p = production.getProduction(0);
        Assertions.assertEquals(INITIAL_PRODUCTION, p);
    }

    @Test
    void production_at_tick_one_is_initial_production() {
        ProductionMsg p = production.getProduction(1);
        Assertions.assertEquals(INITIAL_PRODUCTION, p);
    }

    @Test
    void production_at_tick_one_hundred() {
        ProductionMsg p = production.getProduction(100);
        Assertions.assertEquals(INITIAL_PRODUCTION, p);
    }
}