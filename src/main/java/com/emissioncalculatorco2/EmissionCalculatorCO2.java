package com.emissioncalculatorco2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class EmissionCalculatorCO2 {

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                if (arg.contains("=")) {
                    String[] parts = arg.split("=", 2);
                    params.put(parts[0].substring(2), parts[1]);
                } else if (i + 1 < args.length) {
                    params.put(arg.substring(2), args[i + 1]);
                    i++;
                } else {
                    System.out.println("Missing value for argument " + arg);
                    return;
                }
            } else {
                System.out.println("Unexpected argument format: " + arg);
                return;
            }
        }
        String startCity = params.get("start");
        String endCity = params.get("end");
        String transportationMethod = params.get("transportation-method");

        if (startCity == null || endCity == null || transportationMethod == null) {
            System.out.println("Missing required arguments. Please provide --start <City1> --end <City2> --transportation-method=<method>");
            return;
        }
        try {
            double distance = EmissionCalculatorCO2Utils.getDistance(startCity, endCity);
            double co2 = EmissionCalculatorCO2Utils.calculateCO2(distance, transportationMethod);
            System.out.printf("Your trip caused %.1fkg of CO2-equivalent.%n", co2);
        } catch (IOException e) {
            System.err.println("Error calculating emissions: " + e.getMessage());
            System.exit(1);
        }
    }
}
