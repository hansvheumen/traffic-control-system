package trafficcontrol.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Config {
    private static final String configFilename = "config.properties";
    public static final Properties properties = new Properties();

    private Config() {
    }

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            properties.load(loader.getResourceAsStream(configFilename));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Integer getInteger(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public static Long getLong(String key) {
        return Long.parseLong(properties.getProperty(key));
    }

    public static long getFaseDuration() {
        return getLong("faseDuration");
    }

    private static List<String> getPropertyAsList(String property) {
        return (property != null)
                ? Arrays.stream(property.split(",")).map(String::trim).collect(Collectors.toList())
                : Collections.emptyList();
    }
}
