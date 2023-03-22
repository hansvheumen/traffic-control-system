package trafficcontrol.light.port.in;

import trafficcontrol.common.TrafficLightCommand;

public interface ExecuteTrafficLightCommandUseCase {
    
    void executeCommand(TrafficLightCommand command);

}
