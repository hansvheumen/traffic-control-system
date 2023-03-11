package traficcontrol.domain;

public class Light {
    private final Colour colour;
    private LightState state;

    public Light(Colour colour) {
        this.colour = colour;
        this.state = LightState.OFF;
    }

    public void setState(LightState state) {
        this.state = state;
    }

    public LightState getState() {
        return state;
    }

    public Colour getColour() {
        return colour;
    }

    enum Colour {
        RED, ORANGE, GREEN
    }

    enum LightState{
        ON, OFF, BLINKING
    }
}
