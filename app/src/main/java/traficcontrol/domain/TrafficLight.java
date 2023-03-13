package traficcontrol.domain;

import traficcontrol.domain.Light.Colour;
import traficcontrol.domain.Light.LightState;


public class TrafficLight {
    private String name;
    private Light redLight = new Light(Colour.RED);
    private Light orangeLight = new Light(Colour.ORANGE);
    private Light greenLight = new Light(Colour.GREEN);
    private State state;

    public TrafficLight(String name) {
        this.name = name;
        setState(State.STOP);
    }

    public boolean setState(State state) {
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

    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    enum State {
        STOP, TRANSITION, GO, WARNING
    }

}