package com.emissioncalculatorco2;

import java.util.HashMap;
import java.util.Map;

public class EmissionFactors {
    public static final Map<String, Double> EMISSION_FACTORS_CO2 = loadEmissionFactors();

    private static Map<String, Double> loadEmissionFactors() {
        Map<String, Double> map = new HashMap<>();
        String factors = ConfigLoader.getProperty("emission.co2.factors");
        if (factors != null) {
            String[] pairs = factors.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    try {
                        map.put(keyValue[0].trim(), Double.parseDouble(keyValue[1].trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid emission factor value for " + keyValue[0] + ": " + keyValue[1]);
                    }
                }
            }
        }
        return map;
    }
}
