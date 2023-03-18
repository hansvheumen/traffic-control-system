package trafficcontrol.infrastructure.java;

import trafficcontrol.common.TrafficLightState;

public interface SendExecuteCommandJava {
    void executeCommand(TrafficLightState command);
}
