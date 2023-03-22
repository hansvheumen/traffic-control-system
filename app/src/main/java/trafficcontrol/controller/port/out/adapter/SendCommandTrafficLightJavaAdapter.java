package trafficcontrol.controller.port.out.adapter;

import trafficcontrol.common.TrafficAdapter;
import trafficcontrol.common.TrafficLightState;
import trafficcontrol.controller.port.out.SendCommandTrafficLightPort;
import trafficcontrol.light.port.in.adapter.ExecuteTrafficLightCommandJavaAdapter;
import trafficcontrol.util.TrafficLogger;

public class SendCommandTrafficLightJavaAdapter implements SendCommandTrafficLightPort {

    private final ExecuteTrafficLightCommandJavaAdapter executeTrafficLightCommandJavaAdapter;

    public SendCommandTrafficLightJavaAdapter(ExecuteTrafficLightCommandJavaAdapter sendExecuteCommand) {
        this.executeTrafficLightCommandJavaAdapter = sendExecuteCommand;
    }

    @Override
    public void sendState(TrafficLightState state) {
        String wrapMessageForTransport = TrafficAdapter.wrapMessageForTransport(state.name());
        executeTrafficLightCommandJavaAdapter.executeCommand(wrapMessageForTransport);
        TrafficLogger.log("SendCommandTrafficLightJavaAdapter.sendState() {}", state);
    }

}