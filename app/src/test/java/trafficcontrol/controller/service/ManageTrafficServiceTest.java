package trafficcontrol.controller.service;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import trafficcontrol.common.TrafficLightState;
import trafficcontrol.controller.port.out.SendCommandTrafficLightPort;

// ExtendWith(MockitoExtension.class)
public class ManageTrafficServiceTest {

    private static ManageTrafficService sut;
    // @Captor
    @Mock
    private static SendCommandTrafficLightPort sendCommandTrafficLightEast;
    // @Captor
    @Spy
    private static SendCommandTrafficLightPort sendCommandTrafficLightWest;


    @BeforeAll
    static void setup() {

    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        sut = new ManageTrafficService();
        sut.setTrafficLightEast(sendCommandTrafficLightEast);
        sut.setTrafficLightWest(sendCommandTrafficLightWest);
        sut.setFaseDuration(1);
    }

    @Test
    void testFsmAllStates_ok() throws InterruptedException {

        System.out.println("State: INIT");
        sut.fsm();
        verify(sendCommandTrafficLightEast).sendState(TrafficLightState.STOP);
        verify(sendCommandTrafficLightWest).sendState(TrafficLightState.STOP);

        waitAbit();
        System.out.println("State: DRIVING_EW");
        sut.fsm();
        verify(sendCommandTrafficLightEast).sendState(TrafficLightState.GO);

        System.out.println("State: PREPARE_STOP_E");
        waitAbit();
        sut.fsm();
        verify(sendCommandTrafficLightEast).sendState(TrafficLightState.TRANSITION);

        System.out.println("State: DRIVING_WE");
        waitAbit();
        sut.fsm();
        verify(sendCommandTrafficLightWest).sendState(TrafficLightState.GO);

        System.out.println("State: PREPARE_STOP_W");
        waitAbit();
        sut.fsm();
        verify(sendCommandTrafficLightWest).sendState(TrafficLightState.TRANSITION);

        System.out.println("State: DRIVING_EW");
        waitAbit();
        sut.fsm();
        verify(sendCommandTrafficLightEast, atMost(2)).sendState(TrafficLightState.GO);

    }

    @Test
    void testHandleMessage() {

    }

    @Test
    void testRun() throws InterruptedException, IOException {
        // sendCommandTrafficLightEast = new SendCommandTrafficLightJavaAdapter(null);
        // sendCommandTrafficLightWest = new SendCommandTrafficLightJavaAdapter(null);
        // trafficController.setTrafficLightEast(sendCommandTrafficLightEast);
        // trafficController.setTrafficLightWest(sendCommandTrafficLightWest);
        // trafficController = new ManageTrafficService();
        System.out.println("testRun: " + "start");
        sut.run();
        Thread.sleep(5 * 1000);
        System.out.println("testRun: " + 1);
        sut.close();
    }
    /*
     * Needed because of faseDuration = 1; in ManageTrafficService
     */
    private void waitAbit() throws InterruptedException {        
        Thread.sleep(2);
    }
}
