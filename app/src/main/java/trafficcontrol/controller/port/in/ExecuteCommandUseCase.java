package trafficcontrol.controller.port.in;

import trafficcontrol.controller.service.ManageTrafficService.TrafficState;

public interface ExecuteCommandUseCase {

    void executeCommand(TrafficState stateCommand);
    
}
