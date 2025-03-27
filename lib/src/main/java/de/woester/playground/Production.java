package de.woester.playground;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Production {

    private final NavigableMap<Long, ProductionMsg> history = new TreeMap<>();
    private final Buildings buildings;

    public Production(Buildings buildings) {
        this.buildings = buildings;
    }

    public ProductionMsg getProduction(long tick) {
        if (tick < 0) {
            throw new IllegalArgumentException("tick must be a positive number");
        }

        Map.Entry<Long, ProductionMsg> lastKnown = history.floorEntry(tick);
        if (lastKnown != null && lastKnown.getKey() == tick) {
            return lastKnown.getValue();
        }

        ProductionMsg result = new ProductionMsg(0, 0, 0);
        BuildingCountersMsg buildingsAtTick = buildings.getBuildings(tick);
        for (Map.Entry<Building, Long> counter : buildingsAtTick.counters().entrySet()) {
            Building building = counter.getKey();
            ProductionMsg oneBuildingProd = building.getDefinition().production();
            ProductionMsg allBuildingsProd = multiply(oneBuildingProd, counter.getValue());
            // TODO take resource depletion into account
            result = sum(result, allBuildingsProd);
        }

        history.put(tick, result);
        return result;
    }

    public static ProductionMsg sum(ProductionMsg p1, ProductionMsg p2) {
        // stocks can never be negative
        return new ProductionMsg(
                p1.iron() + p2.iron(),
                p1.energy() + p2.energy(),
                p1.population() + p2.population()
        );
    }

    public static ProductionMsg multiply(ProductionMsg p, long factor) {
        // stocks can never be negative
        return new ProductionMsg(
                factor * p.iron(),
                factor * p.energy(),
                factor * p.population()
        );
    }
}
