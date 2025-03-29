package de.woester.playground.input;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class InputService {

    private final Queue<InputMsg> queue = new LinkedList<>();

    public synchronized void add(InputMsg inputMsg) {
        switch (inputMsg) {
            case CreateBuildingMsg msg -> {
                long count = queue.stream()
                        .filter(i -> i instanceof CreateBuildingMsg)
                        .count();
                queue.add(new CreateBuildingMsg(msg.inputTick(), msg.inputUuid(), msg.building(), count));
            }
        }
    }

    /**
     * Returns all InputMsg to be processed during the given tick. Inputs happen at a certain tick and will be
     * processed during the next tick.
     *
     * @param tick the given tick counter
     * @return all InputMsg to be processed
     */
    public synchronized Queue<InputMsg> peek(long tick) {
        return queue.stream()
                .filter(i -> i.inputTick() < tick)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
