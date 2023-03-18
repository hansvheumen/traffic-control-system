package trafficcontrol.light.port.in;

import trafficcontrol.common.TrafficLightState;
import trafficcontrol.light.domain.TrafficLight;

public interface ExecuteCommandUseCase {
    
    void executeCommand(TrafficLightState command);

}
