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
import trafficcontrol.controller.port.out.adapter.SendCommandTrafficLightJavaAdapter;
import trafficcontrol.infrastructure.java.ExchangeTrafficLightCommandInfraJava;
import trafficcontrol.light.domain.TrafficLight;

// ExtendWith(MockitoExtension.class)
public class ManageTrafficServiceTest {

    private static ManageTrafficService trafficController;
    // @Captor
    @Spy
    private static SendCommandTrafficLightPort sendCommandTrafficLightEast;
    // @Captor
    @Spy
    private static SendCommandTrafficLightPort sendCommandTrafficLightWest;

    @Mock
    private static ExchangeTrafficLightCommandInfraJava sendExecuteCommandJavaEast;
    @Spy
    private static ExchangeTrafficLightCommandInfraJava sendExecuteCommandJavaWest;

    @Mock
    private static TrafficLight trafficLightEast = new TrafficLight("East");
    @Mock
    private static TrafficLight trafficLightWest = new TrafficLight("West");

    @BeforeAll
    static void setup() {

    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        trafficController = new ManageTrafficService();
        trafficController.setTrafficLightEast(sendCommandTrafficLightEast);
        trafficController.setTrafficLightWest(sendCommandTrafficLightWest);
        trafficController.setFaseDuration(1);
    }

    @Test
    void testFsmAllStates_ok() throws InterruptedException {

        System.out.println("State: INIT");
        trafficController.fsm();
        verify(sendCommandTrafficLightEast).sendState(TrafficLightState.STOP);
        verify(sendCommandTrafficLightWest).sendState(TrafficLightState.STOP);

        waitAbit();
        System.out.println("State: DRIVING_EW");
        trafficController.fsm();
        verify(sendCommandTrafficLightEast).sendState(TrafficLightState.GO);

        System.out.println("State: PREPARE_STOP_E");
        waitAbit();
        trafficController.fsm();
        verify(sendCommandTrafficLightEast).sendState(TrafficLightState.TRANSITION);

        System.out.println("State: DRIVING_WE");
        waitAbit();
        trafficController.fsm();
        verify(sendCommandTrafficLightWest).sendState(TrafficLightState.GO);

        System.out.println("State: PREPARE_STOP_W");
        waitAbit();
        trafficController.fsm();
        verify(sendCommandTrafficLightWest).sendState(TrafficLightState.TRANSITION);

        System.out.println("State: DRIVING_EW");
        waitAbit();
        trafficController.fsm();
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
        trafficController.run();
        Thread.sleep(5 * 1000);
        System.out.println("testRun: " + 1);
        trafficController.close();
    }
    /*
     * Needed because of faseDuration = 1; in ManageTrafficService
     */
    private void waitAbit() throws InterruptedException {        
        Thread.sleep(2);
    }
}
