package traficcontrol;

import traficcontrol.domain.TrafficController;
import traficcontrol.domain.TrafficLight;

public class App {

    private static TrafficController trafficController;
    private static TrafficLight trafficLightEast;
    private static TrafficLight trafficLightWest;

    public static void main(String[] args) {
        trafficLightEast = new TrafficLight("East");
        trafficLightWest = new TrafficLight("West");

        trafficController = new TrafficController(trafficLightEast, trafficLightWest);
        trafficController.run();
    }
}
