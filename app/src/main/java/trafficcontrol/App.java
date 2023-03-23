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

        public static void main(String[] args) throws InterruptedException {

                // Controller
                trafficController = new ManageTrafficService();

                // TrafficLight
                trafficLightEast = new TrafficLight("East");
                trafficLightWest = new TrafficLight("West");
                excecuteTrafficLightCommandServiceEast = new ExecuteTrafficLightCommandService(trafficLightEast);
                excecuteTrafficLightCommandServiceWest = new ExecuteTrafficLightCommandService(trafficLightWest);

                configJavaAdapters();

                int nrOfIterations = 5; // 0 for infinite (run)
                switch (nrOfIterations) {
                        case 0:
                                trafficController.run();
                                break;
                        default:
                                for (int i = 0; i < nrOfIterations; i++) {
                                        trafficController.fsm();
                                        Thread.sleep(1000);
                                }
                                break;
                }

        }

        private static void configJavaAdapters() {

                handleMessageJavaAdapter = new HandleMessageJavaAdapter(trafficController);

                sendMessagePortEast = new SendMessageJavaAdapter(handleMessageJavaAdapter);
                sendMessagePortWest = new SendMessageJavaAdapter(handleMessageJavaAdapter);

                executeTrafficLightCommandJavaAdapterEast = new ExecuteTrafficLightCommandJavaAdapter(
                                excecuteTrafficLightCommandServiceEast);
                executeTrafficLightCommandJavaAdapterWest = new ExecuteTrafficLightCommandJavaAdapter(
                                excecuteTrafficLightCommandServiceWest);

                sendCommandTrafficLightEast = new SendCommandTrafficLightJavaAdapter(
                                executeTrafficLightCommandJavaAdapterEast);
                sendCommandTrafficLightWest = new SendCommandTrafficLightJavaAdapter(
                                executeTrafficLightCommandJavaAdapterWest);

                trafficController.setTrafficLightEast(sendCommandTrafficLightEast);
                trafficController.setTrafficLightWest(sendCommandTrafficLightWest);

                excecuteTrafficLightCommandServiceEast.setSendMessagePort(sendMessagePortEast);
                excecuteTrafficLightCommandServiceWest.setSendMessagePort(sendMessagePortWest);

        }

}
