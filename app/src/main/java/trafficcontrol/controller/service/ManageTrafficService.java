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
    private long start;
    private long faseDuration = 1;
    private TrafficState trafficState;
    private TrafficState previousTrafficState;
    private boolean keepLooping = true;

    public ManageTrafficService(SendCommandTrafficLightPort trafficLightEast,
            SendCommandTrafficLightPort trafficLightWest) {
        this.trafficLightEast = trafficLightEast;
        this.trafficLightWest = trafficLightWest;
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
        return System.currentTimeMillis() - start > duration;
    }

    private void resetTimer() {
        start = System.currentTimeMillis();
    }

    private void stopEW() {
        trafficLightEast.sendState(TrafficLightState.STOP);
    }

    private void stopWE() {
        trafficLightWest.sendState(TrafficLightState.STOP);
    }

    private void drivingEW() {
        trafficLightEast.sendState(TrafficLightState.GO);
        trafficLightWest.sendState(TrafficLightState.STOP);
    }

    private void preparingStopE() {
        trafficLightEast.sendState(TrafficLightState.TRANSITION);
    }

    private void drivingWE() {
        trafficLightWest.sendState(TrafficLightState.GO);
        trafficLightEast.sendState(TrafficLightState.STOP);
    }

    private void preparingStopW() {
        trafficLightWest.sendState(TrafficLightState.TRANSITION);
    }

    private void outOfOrder() {
        trafficLightEast.sendState(TrafficLightState.WARNING);
        trafficLightWest.sendState(TrafficLightState.WARNING);
    }

    private boolean stateChanged() {
        return previousTrafficState != trafficState;
    }

    private void saveState() {
        previousTrafficState = trafficState;
    }

    private void effectuateState() {
        String message = String.format("Traffic State: %s,    \n", this.trafficState);
        TrafficLogger.log(message);
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
        // TODO Auto-generated method stub  handle ack nack
        // throw new UnsupportedOperationException("Unimplemented method 'handleMessage'");
    }

    @Override
    public void close() throws IOException {
        keepLooping = false;
        TrafficLogger.log("Closing TrafficController");
    }

    public void setFaseDuration(long faseDuration) {
        this.faseDuration = faseDuration;
    }

}
