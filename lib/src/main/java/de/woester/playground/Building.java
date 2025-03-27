package de.woester.playground;

public enum Building {

    IRON_MINE(new BuildingMsg("", 2L, new ProductionMsg(1, -1, 0))),
    ;

    private final BuildingMsg definition;

    Building(BuildingMsg definition) {
        this.definition = new BuildingMsg(toString(), definition.ticksToBuild(), definition.production());
    }

    BuildingMsg getDefinition() {
        return definition;
    }
}
