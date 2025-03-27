package de.woester.playground;

public record BuildingMsg(
        String id,
        long ticksToBuild,
        ProductionMsg production
) {
}
