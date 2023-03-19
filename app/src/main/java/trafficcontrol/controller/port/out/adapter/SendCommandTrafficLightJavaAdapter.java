package trafficcontrol.controller.port.out.adapter;

import trafficcontrol.common.TrafficLightState;
import trafficcontrol.controller.port.out.SendCommandTrafficLightPort;
import trafficcontrol.infrastructure.java.SendExecuteCommandJava;
import trafficcontrol.util.TrafficLogger;

public class SendCommandTrafficLightJavaAdapter implements SendCommandTrafficLightPort {

    private final SendExecuteCommandJava sendExecuteCommand;

    public SendCommandTrafficLightJavaAdapter(SendExecuteCommandJava sendExecuteCommand) {
        this.sendExecuteCommand = sendExecuteCommand;
    }

    @Override
    public void sendState(TrafficLightState state) {
        TrafficLogger.log("SendCommandTrafficLightJavaAdapter.sendState()" + state);
        // sendExecuteCommand.executeCommand(state);
    }

}