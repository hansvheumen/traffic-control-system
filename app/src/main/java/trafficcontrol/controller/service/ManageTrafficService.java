package trafficcontrol.controller.service;

import java.io.Closeable;
import java.io.IOException;

import trafficcontrol.common.ResponseMessage;
import trafficcontrol.common.TrafficLightState;
import trafficcontrol.controller.port.in.HandleMessageUseCase;
import trafficcontrol.controller.port.out.SendCommandTrafficLightPort;
import trafficcontrol.util.Config;
import trafficcontrol.util.TrafficLogger;

public class ManageTrafficService implements HandleMessageUseCase, Closeable {
    private SendCommandTrafficLightPort trafficLightEast;
    private SendCommandTrafficLightPort trafficLightWest;
    private long startTrafficStateFase;
    private long faseDuration = 1;
    private TrafficState trafficState;
    private TrafficState previousTrafficState;
    private boolean keepLooping = true;

    private long startTimout;

    public ManageTrafficService() {
        resetTimer();
        trafficState = TrafficState.INIT;
        previousTrafficState = TrafficState.INIT;
        faseDuration = Config.getFaseDuration();
    }

    public void run() {
        while (keepLooping) {
            this.fsm();
        }
    }

    public void fsm() {
        switch (trafficState) {
            case INIT:
                resetTimer();
                stopEW();
                stopWE();
                saveState();
                effectuateState();
                trafficState = TrafficState.DRIVING_EW;
                break;
            case DRIVING_EW:
                if (stateChanged()) {
                    saveState();
                    drivingEW();
                    effectuateState();
                }
                if (timeElapsed(faseDuration)) {
                    resetTimer();
                    trafficState = TrafficState.PREPARE_STOP_E;
                }
                break;
            case PREPARE_STOP_E:
                if (stateChanged()) {
                    saveState();
                    preparingStopE();
                    effectuateState();
                }
                if (timeElapsed(faseDuration)) {
                    resetTimer();
                    trafficState = TrafficState.DRIVING_WE;
                }
                break;
            case DRIVING_WE:
                if (stateChanged()) {
                    saveState();
                    drivingWE();
                    effectuateState();
                }
                if (timeElapsed(faseDuration)) {
                    resetTimer();
                    trafficState = TrafficState.PREPARE_STOP_W;
                }
                break;
            case PREPARE_STOP_W:
                if (stateChanged()) {
                    saveState();
                    preparingStopW();
                    effectuateState();
                }
                if (timeElapsed(faseDuration)) {
                    resetTimer();
                    trafficState = TrafficState.DRIVING_EW;
                }
                break;
            case OUT_OF_ORDER:
                if (stateChanged()) {
                    saveState();
                    outOfOrder();
                    effectuateState();
                    trafficState = TrafficState.TERM;
                }
                break;
            case TERM:
                keepLooping = false;
                break;

            default:
                System.out.println("DEFAULT");
                break;
        }
    }

    private boolean timeElapsed(long duration) {
        return System.currentTimeMillis() - startTrafficStateFase > duration;
    }

    private void resetTimer() {
        startTrafficStateFase = System.currentTimeMillis();
    }

    private void stopEW() {
        sendMessage(trafficLightEast, TrafficLightState.STOP);
    }

    private void stopWE() {
        sendMessage(trafficLightWest, TrafficLightState.STOP);
    }

    private void drivingEW() {
        sendMessage(trafficLightWest, TrafficLightState.STOP);
        sendMessage(trafficLightEast, TrafficLightState.GO);
    }

    private void preparingStopE() {
        sendMessage(trafficLightEast, TrafficLightState.TRANSITION);
    }

    private void drivingWE() {
        sendMessage(trafficLightEast, TrafficLightState.STOP);
        sendMessage(trafficLightWest, TrafficLightState.GO);
    }

    private void preparingStopW() {
        sendMessage(trafficLightWest, TrafficLightState.TRANSITION);
    }

    private void outOfOrder() {
        sendMessage(trafficLightEast, TrafficLightState.WARNING);
        sendMessage(trafficLightWest, TrafficLightState.WARNING);
    }

    private boolean stateChanged() {
        return previousTrafficState != trafficState;
    }

    private void saveState() {
        previousTrafficState = trafficState;
    }

    private void effectuateState() {
        TrafficLogger.log("Traffic State: {}", TrafficLogger.redString(this.trafficState.name()));
    }

    private ResponseMessage sendMessage(SendCommandTrafficLightPort trafficLight, TrafficLightState state) {

        final Integer nrOfTries = Config.getInteger("nrOfTries");
        final int timeoutLimit = Config.getInteger("timeoutLimit");       
        trafficLight.sendState(state);
        return ResponseMessage.ACK;
    }

    public enum TrafficState {
        INIT,
        PREPARE_STOP_W,
        DRIVING_EW,
        PREPARE_STOP_E,
        DRIVING_WE,
        OUT_OF_ORDER,
        TERM
    }

    @Override
    public void handleMessage(ResponseMessage message) {
        // TODO Auto-generated method stub handle ack nack
        // throw new UnsupportedOperationException("Unimplemented method
        // 'handleMessage'");
    }

    @Override
    public void close() throws IOException {
        this.keepLooping = false;
        TrafficLogger.log("Closing TrafficController");
    }

    public void setFaseDuration(long faseDuration) {
        this.faseDuration = faseDuration;
    }

    public void setTrafficLightEast(SendCommandTrafficLightPort trafficLightEast) {
        this.trafficLightEast = trafficLightEast;
    }

    public void setTrafficLightWest(SendCommandTrafficLightPort trafficLightWest) {
        this.trafficLightWest = trafficLightWest;
    }

}
