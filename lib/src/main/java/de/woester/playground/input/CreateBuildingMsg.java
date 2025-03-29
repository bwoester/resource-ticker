package de.woester.playground.input;

import de.woester.playground.Building;

import java.util.UUID;

public record CreateBuildingMsg(long inputTick, UUID inputUuid, Building building, long previousQueueSize) implements InputMsg {
    public CreateBuildingMsg(long inputTick, UUID inputUuid, Building building) {
        this(inputTick, inputUuid, building, -1);
    }
}
