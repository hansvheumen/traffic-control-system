package trafficcontrol.light.service;

import trafficcontrol.common.ResponseMessage;
import trafficcontrol.common.TrafficLightCommand;
import trafficcontrol.common.TrafficLightState;
import trafficcontrol.common.TrafficAdapter;
import trafficcontrol.light.domain.TrafficLight;
import trafficcontrol.light.port.in.ExecuteTrafficLightCommandUseCase;
import trafficcontrol.light.port.out.SendMessagePort;

public class ExecuteTrafficLightCommandService implements ExecuteTrafficLightCommandUseCase {

    private final TrafficLight trafficLight;
    private SendMessagePort sendMessagePort;

    public ExecuteTrafficLightCommandService(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void executeCommand(TrafficLightCommand command) {
        if (TrafficLightCommand.UNKNOWN.equals(command)) {
            sendMessagePort.sendMessage(ResponseMessage.NACK);
        } else {
            TrafficLightState state = TrafficAdapter.mapEnum(command, TrafficLightState.class);
            trafficLight.setState(state);
            sendMessagePort.sendMessage(ResponseMessage.ACK);
        }
    }

    public void setSendMessagePort(SendMessagePort sendMessagePort) {
        this.sendMessagePort = sendMessagePort;
    }

}
