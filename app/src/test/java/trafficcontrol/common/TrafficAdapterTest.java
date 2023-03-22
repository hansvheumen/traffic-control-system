package trafficcontrol.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TrafficAdapterTest {

    @ParameterizedTest
    @ValueSource(strings = { "$command;", "$q;", " $command;", " $q; " }) 
    void testIsValidMessageSyntax_withValidInput(String input) {
        boolean restult = TrafficAdapter.isValidMessageSyntax(input);
        assertTrue(restult);
    }
    @ParameterizedTest
    @ValueSource(strings = { "$;", "$q", "$q;4 ", " $command", " $q " }) 
    void testIsValidMessageSyntax_withInValidInput(String input) {
        boolean restult = TrafficAdapter.isValidMessageSyntax(input);
        assertFalse(restult);
    }

    @Test
    void testMapEnum() {

    }

    @Test
    void testRemoveStartAndEndIndicator() {

    }
}
