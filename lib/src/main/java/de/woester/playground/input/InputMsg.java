package de.woester.playground.input;

import java.util.UUID;

public sealed interface InputMsg permits CreateBuildingMsg {

    /**
     * Input that is received will be processed during the next input processing phase.
     * @return tick of input reception
     */
    long inputTick();

    /**
     * @return uuid for the input, required for feedback
     */
    UUID inputUuid();

}
