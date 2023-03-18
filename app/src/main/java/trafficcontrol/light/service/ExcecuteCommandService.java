package trafficcontrol.light.service;

import trafficcontrol.common.Message;
import trafficcontrol.common.TrafficLightState;
import trafficcontrol.light.domain.TrafficLight;

import trafficcontrol.light.port.in.ExecuteCommandUseCase;
import trafficcontrol.light.port.out.SendMessagePort;

public class ExcecuteCommandService implements ExecuteCommandUseCase {

    private final TrafficLight trafficLight;
    private final SendMessagePort sendResponsePort;

    public ExcecuteCommandService(TrafficLight trafficLight, SendMessagePort sendResponsePort) {
        this.trafficLight = trafficLight;
        this.sendResponsePort = sendResponsePort;
    }

    @Override
    public void executeCommand(TrafficLightState command) {
        trafficLight.setState(command);
        sendResponsePort.sendMessage(Message.ACK);
    }

}
