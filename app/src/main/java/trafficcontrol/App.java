package trafficcontrol;

import trafficcontrol.controller.port.in.adapter.HandleMessageJavaAdapter;
import trafficcontrol.controller.port.out.SendCommandTrafficLightPort;
import trafficcontrol.controller.port.out.adapter.SendCommandTrafficLightJavaAdapter;
import trafficcontrol.controller.service.ManageTrafficService;
import trafficcontrol.light.domain.TrafficLight;
import trafficcontrol.light.port.in.adapter.ExecuteTrafficLightCommandJavaAdapter;
import trafficcontrol.light.port.out.SendMessagePort;
import trafficcontrol.light.port.out.adapter.SendMessageJavaAdapter;
import trafficcontrol.light.service.ExecuteTrafficLightCommandService;

public class App {

    // TrafficController
    private static HandleMessageJavaAdapter handleMessageJavaAdapter;
    private static ManageTrafficService trafficController;
    private static SendCommandTrafficLightPort sendCommandTrafficLightEast;
    private static SendCommandTrafficLightPort sendCommandTrafficLightWest;

    // TrafficLight
    private static TrafficLight trafficLightEast;
    private static TrafficLight trafficLightWest;
    private static ExecuteTrafficLightCommandJavaAdapter executeTrafficLightCommandJavaAdapterEast;
    private static ExecuteTrafficLightCommandJavaAdapter executeTrafficLightCommandJavaAdapterWest;
    private static ExecuteTrafficLightCommandService excecuteTrafficLightCommandServiceEast;
    private static ExecuteTrafficLightCommandService excecuteTrafficLightCommandServiceWest;
    private static SendMessagePort sendMessagePortEast;
    private static SendMessagePort sendMessagePortWest;

    public static void main(String[] args) {
        
        // TODO optimize this code: now mutiple instances of the same object are created

        sendCommandTrafficLightEast = new SendCommandTrafficLightJavaAdapter(executeTrafficLightCommandJavaAdapterEast);
        sendCommandTrafficLightWest = new SendCommandTrafficLightJavaAdapter(executeTrafficLightCommandJavaAdapterWest);
        trafficController = new ManageTrafficService(sendCommandTrafficLightEast, sendCommandTrafficLightWest);
        handleMessageJavaAdapter = new HandleMessageJavaAdapter(trafficController);

        // TrafficLight
        trafficLightEast = new TrafficLight("East");
        trafficLightWest = new TrafficLight("West");

        excecuteTrafficLightCommandServiceEast = new ExecuteTrafficLightCommandService(trafficLightEast,
                sendMessagePortEast);
        excecuteTrafficLightCommandServiceWest = new ExecuteTrafficLightCommandService(trafficLightWest,
                sendMessagePortWest);
        sendMessagePortEast = new SendMessageJavaAdapter(handleMessageJavaAdapter);
        sendMessagePortWest = new SendMessageJavaAdapter(handleMessageJavaAdapter);

        excecuteTrafficLightCommandServiceEast = new ExecuteTrafficLightCommandService(trafficLightEast,
                sendMessagePortEast);
        excecuteTrafficLightCommandServiceWest = new ExecuteTrafficLightCommandService(trafficLightWest,
                sendMessagePortWest);
        executeTrafficLightCommandJavaAdapterEast = new ExecuteTrafficLightCommandJavaAdapter(
                excecuteTrafficLightCommandServiceEast);
        executeTrafficLightCommandJavaAdapterWest = new ExecuteTrafficLightCommandJavaAdapter(
                excecuteTrafficLightCommandServiceWest);

        // TrafficController

        sendCommandTrafficLightEast = new SendCommandTrafficLightJavaAdapter(executeTrafficLightCommandJavaAdapterEast);
        sendCommandTrafficLightWest = new SendCommandTrafficLightJavaAdapter(executeTrafficLightCommandJavaAdapterWest);
        trafficController = new ManageTrafficService(sendCommandTrafficLightEast, sendCommandTrafficLightWest);
        handleMessageJavaAdapter = new HandleMessageJavaAdapter(trafficController);

        // TrafficLight
        trafficLightEast = new TrafficLight("East");
        trafficLightWest = new TrafficLight("West");

        excecuteTrafficLightCommandServiceEast = new ExecuteTrafficLightCommandService(trafficLightEast,
                sendMessagePortEast);
        excecuteTrafficLightCommandServiceWest = new ExecuteTrafficLightCommandService(trafficLightWest,
                sendMessagePortWest);
        sendMessagePortEast = new SendMessageJavaAdapter(handleMessageJavaAdapter);
        sendMessagePortWest = new SendMessageJavaAdapter(handleMessageJavaAdapter);
        trafficController = new ManageTrafficService(sendCommandTrafficLightEast, sendCommandTrafficLightWest);

        trafficController.run();
        // trafficController.fsm();
    }
}
