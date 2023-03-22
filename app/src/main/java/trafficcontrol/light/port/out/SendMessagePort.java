package trafficcontrol.light.port.out;

import trafficcontrol.common.ResponseMessage;

public interface SendMessagePort {

    void sendMessage(ResponseMessage message);

}