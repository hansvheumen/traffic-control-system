package trafficcontrol;


import java.util.concurrent.ExecutorCompletionService;

import org.checkerframework.checker.units.qual.s;

import trafficcontrol.controller.domain.TrafficController;
import trafficcontrol.controller.port.in.adapter.ExecuteCommandJavaAdapter;
import trafficcontrol.controller.port.out.SendCommandTrafficLightPort;
import trafficcontrol.light.domain.TrafficLight;

public class App {

    private static TrafficController trafficController;
    private static SendCommandTrafficLightPort sendCommandTrafficLightEast;
    private static SendCommandTrafficLightPort sendCommandTrafficLightWest;

    private static ExecutorCompletionService executorCompletionService;

    public static void main(String[] args) {


        trafficLightEast = new ExecuteCommandJavaAdapter(null)
        trafficLightWest = new TrafficLight("West");

        trafficController = new TrafficController(trafficLightEast, trafficLightWest);
        trafficController.run();
    }
}
