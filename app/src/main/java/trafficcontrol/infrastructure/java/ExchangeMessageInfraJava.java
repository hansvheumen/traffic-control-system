package trafficcontrol.infrastructure.java;

import trafficcontrol.common.ResponseMessage;

public interface ExchangeMessageInfraJava {
    void handleMessage(ResponseMessage message);
}
