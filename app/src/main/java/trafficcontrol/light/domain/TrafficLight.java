package trafficcontrol.light.domain;

import trafficcontrol.common.TrafficLightState;
import trafficcontrol.light.domain.Light.Colour;
import trafficcontrol.light.domain.Light.LightState;
import trafficcontrol.util.TrafficLogger;

public class TrafficLight {
    private String name;
    private Light redLight = new Light(Colour.RED);
    private Light orangeLight = new Light(Colour.ORANGE);
    private Light greenLight = new Light(Colour.GREEN);
    private TrafficLightState state;

    public TrafficLight(String name) {
        this.name = name;
        setState(TrafficLightState.STOP);
    }

    public boolean setState(TrafficLightState state) {
        TrafficLogger.log("TrafficLight {} state changed from to {}", name, state);
        this.state = state;
        switch (state) {
            case STOP:
                redLight.setState(LightState.ON);
                orangeLight.setState(LightState.OFF);
                greenLight.setState(LightState.OFF);
                break;
            case TRANSITION:
                redLight.setState(LightState.OFF);
                orangeLight.setState(LightState.ON);
                greenLight.setState(LightState.OFF);
                break;
            case GO:
                redLight.setState(LightState.OFF);
                orangeLight.setState(LightState.OFF);
                greenLight.setState(LightState.ON);
                break;
            case WARNING:
                redLight.setState(LightState.OFF);
                orangeLight.setState(LightState.BLINKING);
                greenLight.setState(LightState.OFF);
                break;

        }
        return true;
    }

    public TrafficLightState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

}