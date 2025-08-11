package com.emissioncalculatorco2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getResourceAsStream("/config.properties")) {
            if (input == null) {
                System.err.println("Unable to find config.properties");
            } else {
                props.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
