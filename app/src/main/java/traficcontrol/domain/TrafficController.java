package traficcontrol.domain;

import traficcontrol.util.TrafficLogger;

public class TrafficController {
    private TrafficLight trafficLightEast;
    private TrafficLight trafficLightWest;
    private long start;
    private long duration = 3000;
    private TrafficState trafficState;
    private TrafficState previousTrafficState;

    public TrafficController(TrafficLight trafficLightEast, TrafficLight trafficLightWest) {
        this.trafficLightEast = trafficLightEast;
        this.trafficLightWest = trafficLightWest;
        resetTimer();
        trafficState = TrafficState.INIT;
        previousTrafficState = TrafficState.INIT;
    }

    public void run() {
        while (true) {
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
                if (timeElapsed(duration)) {
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
                if (timeElapsed(duration)) {
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
                if (timeElapsed(duration)) {
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
                if (timeElapsed(duration)) {
                    resetTimer();
                    trafficState = TrafficState.DRIVING_EW;
                }
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
        trafficLightEast.setState(TrafficLight.State.STOP);
    }

    private void stopWE() {
        trafficLightWest.setState(TrafficLight.State.STOP);
    }

    private void drivingEW() {
        trafficLightEast.setState(TrafficLight.State.GO);
        trafficLightWest.setState(TrafficLight.State.STOP);
    }

    private void preparingStopE() {
        trafficLightEast.setState(TrafficLight.State.TRANSITION);
    }

    private void drivingWE() {
        trafficLightWest.setState(TrafficLight.State.GO);
        trafficLightEast.setState(TrafficLight.State.STOP);
    }

    private void preparingStopW() {
        trafficLightWest.setState(TrafficLight.State.TRANSITION);
    }

    private void blinking(){
        trafficLightEast.setState(TrafficLight.State.TRANSITION);
        trafficLightWest.setState(TrafficLight.State.TRANSITION);
    }

    private boolean stateChanged() {
        return previousTrafficState != trafficState;
    }

    private void saveState() {
        previousTrafficState = trafficState;
    }

    private void effectuateState() {
        String message = String.format("State: %s,   East: %s    West %s \n", this.trafficState,
                this.trafficLightEast.getState(), this.trafficLightWest.getState());

        TrafficLogger.logger.info(message);
    }

    public enum TrafficState {
        INIT,
        PREPARE_STOP_W,
        DRIVING_EW,
        PREPARE_STOP_E,
        DRIVING_WE,
        TERM
    }
}
