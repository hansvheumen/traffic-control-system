package trafficcontrol.controller.port.in;

import trafficcontrol.common.ResponseMessage;

public interface HandleMessageUseCase {

    void handleMessage(ResponseMessage message);

}
