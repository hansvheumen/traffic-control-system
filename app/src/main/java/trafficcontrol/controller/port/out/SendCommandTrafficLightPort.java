package trafficcontrol.controller.port.out;

import trafficcontrol.common.TrafficLightState;

public interface SendCommandTrafficLightPort {

    void sendState(TrafficLightState state);

}