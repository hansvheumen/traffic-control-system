package trafficcontrol.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class TrafficLightStateTest {
    @Test
    void testGet() {
        String GOstate = "GO";
        Optional<TrafficLightState> optional = TrafficLightState.get(GOstate);

        assertEquals(GOstate, optional.get().name());

        String nonExistingState = "NONSENSE";
        Optional<TrafficLightState> nonsense = TrafficLightState.get(nonExistingState);

        assertEquals(false, nonsense.isPresent());

    }

    @Test
    void testValueOf() {

    }

    @Test
    void testValues() {

    }
}
