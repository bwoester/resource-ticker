package de.woester.playground;

import java.util.Map;
import java.util.Set;

public record ConstructionMsg(Map<Building, Float> progress, Set<Building> finished) {
}
