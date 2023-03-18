package trafficcontrol.controller.port.in.adapter;

import trafficcontrol.common.Message;
import trafficcontrol.controller.port.in.HandleMessageUseCase;
import trafficcontrol.infrastructure.java.SendHandleMessageJava;

public class HandleMessageJavaAdapter implements SendHandleMessageJava{

    private final HandleMessageUseCase handleMessageUseCase;

    public HandleMessageJavaAdapter(HandleMessageUseCase handleMessageUseCase) {
        this.handleMessageUseCase = handleMessageUseCase;
    }

    @Override
    public void handleMessage(Message message) {
        handleMessageUseCase.handleMessage(message);
        
    }
    

}
