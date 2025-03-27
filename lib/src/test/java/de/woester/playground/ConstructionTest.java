package de.woester.playground;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

class ConstructionTest {

    @Test
    void emptyConstructionQueueIsEmpty() {
        Construction construction = new Construction(0, new ConstructionMsg(Collections.emptyMap(), Collections.emptySet()));
        ConstructionMsg c = construction.getConstruction(0);
        Assertions.assertTrue(c.progress().isEmpty());
        Assertions.assertTrue(c.finished().isEmpty());
    }

    @Test
    void emptyConstructionQueueRemainsEmpty() {
        Construction construction = new Construction(0, new ConstructionMsg(Collections.emptyMap(), Collections.emptySet()));
        ConstructionMsg c = construction.getConstruction(1);
        Assertions.assertTrue(c.progress().isEmpty());
        Assertions.assertTrue(c.finished().isEmpty());
    }

    @Test
    void progressIsUpdated() {
        Map<Building, Float> progress = new EnumMap<>(Building.class);
        progress.put(Building.IRON_MINE, 0.0f);
        Construction construction = new Construction(0, new ConstructionMsg(progress, Collections.emptySet()));
        ConstructionMsg at0 = construction.getConstruction(0);
        ConstructionMsg at1 = construction.getConstruction(1);
        ConstructionMsg at2 = construction.getConstruction(2);
        Assertions.assertEquals(0.0f, at0.progress().get(Building.IRON_MINE));
        Assertions.assertTrue(at0.finished().isEmpty());
        Assertions.assertEquals(0.5f, at1.progress().get(Building.IRON_MINE));
        Assertions.assertTrue(at1.finished().isEmpty());
        Assertions.assertTrue(at2.progress().isEmpty());
        Assertions.assertFalse(at2.finished().isEmpty());
    }

    @Test
    void queueBuilding() {
        Construction construction = new Construction(0, new ConstructionMsg(Collections.emptyMap(), Collections.emptySet()));
        construction.queue(1, Building.IRON_MINE);

        ConstructionMsg at0 = construction.getConstruction(0);
        Assertions.assertTrue(at0.progress().isEmpty());
        Assertions.assertTrue(at0.finished().isEmpty());

        ConstructionMsg at1 = construction.getConstruction(1);
        Assertions.assertEquals(0.0f, at1.progress().get(Building.IRON_MINE));
        Assertions.assertTrue(at1.finished().isEmpty());

        ConstructionMsg at2 = construction.getConstruction(2);
        Assertions.assertEquals(0.5f, at2.progress().get(Building.IRON_MINE));
        Assertions.assertTrue(at2.finished().isEmpty());

        ConstructionMsg at3 = construction.getConstruction(3);
        Assertions.assertTrue(at3.progress().isEmpty());
        Assertions.assertFalse(at3.finished().isEmpty());
    }

}