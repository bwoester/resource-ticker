package de.woester.playground.input;

public sealed interface InputMsg permits CreateBuildingMsg {

    /**
     * Input that is received will be processed during the next input processing phase.
     * @return tick of input reception
     */
    long inputTick();

}
