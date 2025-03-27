package de.woester.playground;

import java.util.EnumMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Buildings {

    private final NavigableMap<Long, BuildingCountersMsg> history = new TreeMap<>();
    private final Construction construction;

    public Buildings(long tick, BuildingCountersMsg buildingCountersAtTick, Construction construction) {
        this.construction = construction;
        history.put(tick, buildingCountersAtTick);
    }

    public BuildingCountersMsg getBuildings(long tick) {
        if (tick < 0) {
            throw new IllegalArgumentException("tick must be a positive number");
        }
        Map.Entry<Long, BuildingCountersMsg> lastKnown = history.floorEntry(tick);
        BuildingCountersMsg result = lastKnown.getValue();

        for (long i = lastKnown.getKey() + 1; i <= tick; i++) {
            ConstructionMsg constructionAtI = construction.getConstruction(i);
            if (!constructionAtI.finished().isEmpty()) {
                Map<Building, Long> newBuildingCounters = new EnumMap<>(result.counters());
                for (Building finishedBuilding : constructionAtI.finished()) {
                    newBuildingCounters.compute(finishedBuilding, (b, oldCount) -> oldCount == null ? 1 : oldCount + 1);
                }
                result = new BuildingCountersMsg(newBuildingCounters);
            }
            history.put(i, result);
        }

        return result;
    }

}
