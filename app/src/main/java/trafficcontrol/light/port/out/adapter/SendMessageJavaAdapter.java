package trafficcontrol.light.port.out.adapter;

import trafficcontrol.common.Message;
import trafficcontrol.infrastructure.java.SendHandleMessageJava;
import trafficcontrol.light.port.out.SendMessagePort;

public class SendMessageJavaAdapter implements SendMessagePort {
    private final SendHandleMessageJava sendHandleMessageJava;

    public SendMessageJavaAdapter(SendHandleMessageJava sendHandleMessageJava) {
        this.sendHandleMessageJava = sendHandleMessageJava;
    }

    @Override
    public void sendMessage(Message message) {
        sendHandleMessageJava.handleMessage(message);
    }

}
