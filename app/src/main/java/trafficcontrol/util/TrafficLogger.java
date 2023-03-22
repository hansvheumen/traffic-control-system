package trafficcontrol.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrafficLogger {
    public static final Logger logger = LoggerFactory.getLogger("TrafficLogger");

    public static void log(String message, Object... arguments) {

        System.setProperty("log4j.logger.log4j.level", "ALL");
        System.setProperty("slf4j.logger.log4j.level", "ALL");

        logger.info(message, arguments);
        logger.error(message, arguments);
        logger.debug(message, arguments);
    }
}
