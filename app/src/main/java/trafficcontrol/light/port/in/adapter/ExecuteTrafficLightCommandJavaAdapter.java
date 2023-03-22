package trafficcontrol.light.port.in.adapter;

import trafficcontrol.common.TrafficAdapter;
import trafficcontrol.common.TrafficLightCommand;
import trafficcontrol.common.TrafficLightState;
import trafficcontrol.light.port.in.ExecuteTrafficLightCommandUseCase;

/*
 * This class is an adapter to convert the string message from the infrastructure to the TrafficLightState
 * 
 */
public class ExecuteTrafficLightCommandJavaAdapter {

    private final ExecuteTrafficLightCommandUseCase executeCommandUseCase;

    public ExecuteTrafficLightCommandJavaAdapter(ExecuteTrafficLightCommandUseCase executeCommandUseCase) {
        this.executeCommandUseCase = executeCommandUseCase;
    }

    public void executeCommand(String command) {
        TrafficLightState state = TrafficAdapter.string2TrafficLightState(command);
        if (state != null) {
            TrafficLightCommand trafficLightCommand = TrafficLightCommand.valueOf(state.name());
            executeCommandUseCase.executeCommand(trafficLightCommand);
        } else {
            executeCommandUseCase.executeCommand(TrafficLightCommand.UNKNOWN);
        }
    }

}
