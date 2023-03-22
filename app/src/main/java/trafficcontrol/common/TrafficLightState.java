package trafficcontrol.common;

import java.util.Arrays;
import java.util.Optional;

public enum TrafficLightState {
    STOP, TRANSITION, GO, WARNING;

    /*
     * This method is used to convert a string to a TrafficLightState
     */
    public static Optional<TrafficLightState> get(String name) {
        return Arrays.stream(TrafficLightState.values())
                .filter(env -> env.name().equals(name))
                .findFirst();
    }
}
