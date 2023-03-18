package trafficcontrol.controller.port.in;

import trafficcontrol.controller.domain.TrafficController.TrafficState;

public interface ExecuteCommandUseCase {

    void executeCommand(TrafficState stateCommand);
    
}
