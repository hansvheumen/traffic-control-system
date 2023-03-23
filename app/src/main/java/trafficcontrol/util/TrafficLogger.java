package trafficcontrol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrafficLogger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static final Logger logger = LoggerFactory.getLogger("TrafficLogger");

    public static void log(String message, Object... arguments) {

        System.setProperty("log4j.logger.log4j.level", "ALL");
        System.setProperty("slf4j.logger.log4j.level", "ALL");

        logger.info(message, arguments);
        logger.error(message, arguments);
        logger.debug(message, arguments);
    }

    public static String redString(String message) {
        return ANSI_RED + message + ANSI_RESET;
    }

    public static String yellowString(String message) {
        return ANSI_YELLOW + message + ANSI_RESET;
    }

    public static String cyanString(String message) {
        return ANSI_CYAN + message + ANSI_RESET;
    }

}
