package trafficcontrol.controller.port.in;

import trafficcontrol.common.Message;

public interface HandleMessageUseCase {

    void handleMessage(Message message);

}
