package trafficcontrol.controller.port.in.adapter;

import trafficcontrol.common.ResponseMessage;
import trafficcontrol.common.TrafficAdapter;
import trafficcontrol.controller.port.in.HandleMessageUseCase;
/*
 * This class is an adapter to convert the string message from the infrastructure to the ResponseMessage
 */
public class HandleMessageJavaAdapter {

    private final HandleMessageUseCase handleMessageUseCase;

    public HandleMessageJavaAdapter(HandleMessageUseCase handleMessageUseCase) {
        this.handleMessageUseCase = handleMessageUseCase;
    }

    public void handleMessage(String message) {
        ResponseMessage responseMessage = TrafficAdapter.string2ResponseMessage(message);
        if (responseMessage != null) {
            handleMessageUseCase.handleMessage(responseMessage);
        } else {
            handleMessageUseCase.handleMessage(ResponseMessage.NACK);
        }
    }

}
