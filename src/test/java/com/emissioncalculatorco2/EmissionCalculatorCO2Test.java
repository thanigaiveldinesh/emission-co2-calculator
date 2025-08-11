package com.emissioncalculatorco2;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import static org.junit.Assert.*;

public class EmissionCalculatorCO2Test {

    private static final double DISTANCE_METERS = 1000000.0; // 1000 km trip in meters

    @Test
    public void testLoadConfigProperties() throws IOException {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            assertNotNull("config.properties should be found in classpath", input);
            Properties props = new Properties();
            props.load(input);
            assertTrue(props.containsKey("emission.co2.factors"));
        }
    }

    @Test
    public void testCalculateCO2_AllTransportMethods() {
        for (var entry : EmissionFactors.EMISSION_FACTORS_CO2.entrySet()) {
            String method = entry.getKey();
            double expectedKgCO2 = entry.getValue() * (DISTANCE_METERS / 1000.0); // factor × km
            double actualKgCO2 = EmissionCalculatorCO2Utils.calculateCO2(DISTANCE_METERS, method);
            assertEquals("Failed for method: " + method, expectedKgCO2, actualKgCO2, 0.0001);
        }
    }

    @Test
    public void testCalculateCO2_ZeroDistance() {
        double co2 = EmissionCalculatorCO2Utils.calculateCO2(0.0, "diesel-car-medium");
        assertEquals(0.0, co2, 0.0001);
    }

    @Test
    public void testCalculateCO2_NegativeDistance() {
        double co2 = EmissionCalculatorCO2Utils.calculateCO2(-5000.0, "electric-car-small");
    }
}