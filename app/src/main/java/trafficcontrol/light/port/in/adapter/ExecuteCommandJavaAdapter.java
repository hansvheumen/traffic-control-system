package trafficcontrol.light.port.in.adapter;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import trafficcontrol.common.TrafficLightState;
import trafficcontrol.light.port.in.ExecuteCommandUseCase;

public class ExecuteCommandJavaAdapter {

    private final ExecuteCommandUseCase executeCommandUseCase;

    public ExecuteCommandJavaAdapter(String Name, ExecuteCommandUseCase executeCommandUseCase) {
        this.executeCommandUseCase = executeCommandUseCase;
    }

    public void executeCommand(String command) {
        TrafficLightState state = string2State(command);
        if (state != null) {
            executeCommandUseCase.executeCommand(state);
        } else {
            throw new RuntimeException(command + " is not a valid state. Valid states are: " + enumStatesToStrings() );
        }
    }

    public static TrafficLightState string2State(String command) {
        TrafficLightState result = null;
        for (TrafficLightState state : TrafficLightState.values()) {
            if (state.name().equals(command)) {
                result = state;
                break;
            }
        }
        return result;
    }

    private static Set<String> enumStatesToStrings() {
        EnumSet<TrafficLightState> enumStates = EnumSet.allOf(TrafficLightState.class);
        return enumStates.stream().map(Enum::toString).collect(Collectors.toSet());
    }

}
