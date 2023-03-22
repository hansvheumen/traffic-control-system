package trafficcontrol.light.port.out.adapter;

import trafficcontrol.common.ResponseMessage;
import trafficcontrol.common.TrafficAdapter;
import trafficcontrol.controller.port.in.adapter.HandleMessageJavaAdapter;
import trafficcontrol.light.port.out.SendMessagePort;
import trafficcontrol.util.TrafficLogger;

public class SendMessageJavaAdapter implements SendMessagePort {
    private final HandleMessageJavaAdapter handleMessageJavaAdapter;

    public SendMessageJavaAdapter(HandleMessageJavaAdapter sendHandleMessageInfraJava) {
        this.handleMessageJavaAdapter = sendHandleMessageInfraJava;
    }

    @Override
    public void sendMessage(ResponseMessage message) {
        String wrapMessageForTransport = TrafficAdapter.wrapMessageForTransport(message.name());
        handleMessageJavaAdapter.handleMessage(wrapMessageForTransport);
        TrafficLogger.log("SendMessageJavaAdapter.sendState() {}", message);
    }

}
