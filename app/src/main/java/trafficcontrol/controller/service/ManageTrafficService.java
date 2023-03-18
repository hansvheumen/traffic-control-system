package trafficcontrol.controller.service;

import trafficcontrol.common.Message;
import trafficcontrol.controller.domain.TrafficController;
import trafficcontrol.controller.port.in.HandleMessageUseCase;
import trafficcontrol.controller.port.out.SendCommandTrafficLightPort;

public class ManageTrafficService implements HandleMessageUseCase {
    private final TrafficController trafficController;
    private final SendCommandTrafficLightPort sendCommandTrafficLightPort;

    public ManageTrafficService(TrafficController trafficController,SendCommandTrafficLightPort sendCommandTrafficLightPort) {
        this.trafficController = trafficController;
        this.sendCommandTrafficLightPort = sendCommandTrafficLightPort;
    }

    @Override
    public void handleMessage(Message message) {
        trafficController.handleMessage(message);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleMessage'");
    }

}