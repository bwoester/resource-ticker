package de.woester.playground.input;

import de.woester.playground.Building;

public record CreateBuildingMsg(long inputTick, Building building, long queuedPos) implements InputMsg {
}
