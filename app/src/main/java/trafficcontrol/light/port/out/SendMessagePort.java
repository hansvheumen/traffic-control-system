package trafficcontrol.light.port.out;

import trafficcontrol.common.Message;

public interface SendMessagePort {

    void sendMessage(Message message);

}