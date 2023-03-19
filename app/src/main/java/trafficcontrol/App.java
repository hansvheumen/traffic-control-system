package trafficcontrol;

import trafficcontrol.controller.port.out.SendCommandTrafficLightPort;
import trafficcontrol.controller.port.out.adapter.SendCommandTrafficLightJavaAdapter;
import trafficcontrol.controller.service.ManageTrafficService;

public class App {

    private static ManageTrafficService trafficController;
    private static SendCommandTrafficLightPort sendCommandTrafficLightEast;
    private static SendCommandTrafficLightPort sendCommandTrafficLightWest;

    public static void main(String[] args) {

        sendCommandTrafficLightEast = new SendCommandTrafficLightJavaAdapter(null);
        sendCommandTrafficLightWest = new SendCommandTrafficLightJavaAdapter(null);

        trafficController = new ManageTrafficService(sendCommandTrafficLightEast, sendCommandTrafficLightWest);
        trafficController.run();
    }
}
