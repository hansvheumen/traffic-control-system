package trafficcontrol.infrastructure.java;

import trafficcontrol.common.TrafficLightState;

public interface ExchangeTrafficLightCommandInfraJava {
    void executeCommand(TrafficLightState command);
}
