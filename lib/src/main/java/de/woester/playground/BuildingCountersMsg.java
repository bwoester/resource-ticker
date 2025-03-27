package de.woester.playground;

import java.util.Map;

public record BuildingCountersMsg(Map<Building, Long> counters) {
}
