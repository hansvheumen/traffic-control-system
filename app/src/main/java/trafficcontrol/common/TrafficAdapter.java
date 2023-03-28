package trafficcontrol.common;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TrafficAdapter {

    public static final String MESSAGE_DELIMETER_BEGIN = "$";
    public static final String MESSAGE_DELIMETER_END = ";";

    public static <E extends Enum<E>, F extends Enum<F>> F mapEnum(E enum1, Class<F> enum2Class) {
        return Enum.valueOf(enum2Class, enum1.name());
    }

    /*
     * Strip the command from the message and return it
     * remove the starting $ and the ending ;
     * return null if the message is not valid
     */
    public static String removeStartAndEndIndicator(String message) {
        String command = null;
        if (isValidMessageSyntax(message)) {
            command = message.trim();
            command = command.substring(1, command.length() - 1);
        }
        return command;
    }

    /*
     * massage syntax is valid if it starts with $ and ends with ;
     */
    public static boolean isValidMessageSyntax(String message) {
        message = message.trim();
        return message.startsWith(MESSAGE_DELIMETER_BEGIN) && message.endsWith(MESSAGE_DELIMETER_END)
                && message.length() > 2;
    }

    public static ResponseMessage string2ResponseMessage(String transportMessage) {
        String transportedMessage = TrafficAdapter.removeStartAndEndIndicator(transportMessage);
        ResponseMessage result = null;
        for (ResponseMessage message : ResponseMessage.values()) {
            if (message.name().equals(transportedMessage)) {
                result = message;
                break;
            }
        }
        return result;
    }

    public static TrafficLightState string2TrafficLightState(String transportCommand) {
        String command = TrafficAdapter.removeStartAndEndIndicator(transportCommand);
        TrafficLightState result = null;
        for (TrafficLightState state : TrafficLightState.values()) {
            if (state.name().equals(command)) {
                result = state;
                break;
            }
        }
        return result;
    }

    public static String wrapMessageForTransport(String message) {
        return String.join(MESSAGE_DELIMETER_BEGIN, message.trim(), MESSAGE_DELIMETER_END);
    }

    private static Set<String> enumTrafficLightStateToStrings() {
        EnumSet<TrafficLightState> enumStates = EnumSet.allOf(TrafficLightState.class);
        return enumStates.stream().map(Enum::toString).collect(Collectors.toSet());
    }
}
