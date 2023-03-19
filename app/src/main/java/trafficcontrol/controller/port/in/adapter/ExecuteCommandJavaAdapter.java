package trafficcontrol.controller.port.in.adapter;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import trafficcontrol.controller.port.in.ExecuteCommandUseCase;
import trafficcontrol.controller.service.ManageTrafficService.TrafficState;

public class ExecuteCommandJavaAdapter {

    private final ExecuteCommandUseCase executeCommandUseCase;
    private final Set<String> states = enumStatesToStrings();

    public ExecuteCommandJavaAdapter(ExecuteCommandUseCase executeCommandUseCase) {
        this.executeCommandUseCase = executeCommandUseCase;
    }

    public void executeCommand(String command) {

        TrafficState state = string2State(command);
        if (state != null) {
            executeCommandUseCase.executeCommand(state);
        } else {
            throw new RuntimeException(command + " is not a valid state. Valid states are: " + states);
        }
    }

    public static TrafficState string2State(String command) {
        TrafficState result = null;
        for (TrafficState state : TrafficState.values()) {
            if (state.name().equals(command)) {
                result = state;
                break;
            }
        }
        return result;
    }

    private static Set<String> enumStatesToStrings() {
        EnumSet<TrafficState> enumStates = EnumSet.allOf(TrafficState.class);
        return enumStates.stream().map(Enum::toString).collect(Collectors.toSet());
    }

}
