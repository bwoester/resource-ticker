package de.woester.playground.input;

import java.util.LinkedList;
import java.util.Queue;

public class InputService {

    private final Queue<InputMsg> queue = new LinkedList<>();

    public void add(InputMsg inputMsg) {
        switch (inputMsg) {
          case CreateBuildingMsg msg -> {
              // TODO race condition
              long count = queue.stream()
                      .filter(i -> i instanceof CreateBuildingMsg)
                      .count();
              queue.add(new CreateBuildingMsg(msg.inputTick(), msg.building(), count));
          }
        };
    }

}
