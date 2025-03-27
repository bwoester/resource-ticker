package de.woester.playground;

import java.util.*;

public class Construction {

    // TODO simplify ConstructionMsg
    //  - only one progress at a time
    //  - finished gets its own history
    private final NavigableMap<Long, ConstructionMsg> history = new TreeMap<>();
    // TODO maybe store queueing tick, to calculate malus?
    private Queue<Building> queue = new LinkedList<>();

    public Construction(long tick, ConstructionMsg constructionAtTick) {
        history.put(tick, constructionAtTick);
    }

    public ConstructionMsg getConstruction(long tick) {
        if (tick < 0) {
            throw new IllegalArgumentException("tick must be a positive number");
        }
        Map.Entry<Long, ConstructionMsg> lastKnown = history.floorEntry(tick);
        ConstructionMsg result = lastKnown.getValue();
        for (long i = lastKnown.getKey() + 1; i <= tick; i++) {
            Map<Building, Float> oldProgress = result.progress();
            Map<Building, Float> progressAtI = new EnumMap<>(Building.class);
            Collection<Building> finishedAtI = new ArrayList<>();

            if (oldProgress.isEmpty() && !queue.isEmpty()) {
                Building newConstruction = queue.poll();
                // TODO add a malus to the new construction, if it was queued up
                //  (only starting a new construction after previous ones had been finished should go without malus)
                oldProgress.put(newConstruction, 0.0f);
            }

            // TODO includue effects like energy shortage, ...
            float progressFactor = 1.0f;

            for (Map.Entry<Building, Float> entry : oldProgress.entrySet()) {
                Building building = entry.getKey();
                float progressIncrease = progressFactor * (1f / building.getDefinition().ticksToBuild());
                float newProgress = entry.getValue() + progressIncrease;
                if (newProgress < 1) {
                    progressAtI.put(building, newProgress);
                } else {
                    // TODO maybe this should be its own history
                    finishedAtI.add(building);
                }
            }

            result = new ConstructionMsg(
                    progressAtI,
                    finishedAtI.isEmpty()
                            ? Collections.emptySet()
                            : EnumSet.copyOf(finishedAtI)
            );
            history.put(i, result);
        }

        return result;
    }

    public void queue(long tick, Building building) {
        ConstructionMsg construction = getConstruction(tick);
        queue.add(building);
    }
}
