package com.emissioncalculatorco2;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class EmissionCalculatorCO2Utils {
    public static double getDistance(String startCity, String endCity) throws IOException {
        String orsToken = getApiToken();
        String url = ConfigLoader.getProperty("ors.matrix.url");

        OkHttpClient client = new OkHttpClient();

        String startCoordinates = getCoordinates(startCity);
        String endCoordinates = getCoordinates(endCity);

        String json = String.format("{\"locations\":[[%s],[%s]],\"metrics\":[\"distance\"]}",
                startCoordinates, endCoordinates);

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", orsToken)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            String errorResponse = response.body() != null ? response.body().string() : "No response body";
            throw new IOException("Unexpected code "+ response +" Response body: "+errorResponse);
        }

        String responseBody = response.body().string();
       // System.out.println("Distance API Response: " + responseBody);

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray distances = jsonResponse.getJSONArray("distances");


        Object distValue = distances.getJSONArray(0).get(1);
        if (distValue == JSONObject.NULL) {
            // Throw clear error instead of failing with JSONException
            throw new IOException("Distance not available for the given cities. Please check the input format and city names.");
        }

        try {
            return distances.getJSONArray(0).getDouble(1); // distance in meters
        } catch (Exception e) {
            throw new IOException("Invalid distance value received. Please check the input format and city names.");
        }
    }


    public static String getCoordinates(String city) throws IOException {
        String orsToken = System.getenv("ORS_TOKEN");
        if (orsToken == null) {
            orsToken = ConfigLoader.getProperty("ors.token");
        }
        String baseUrl = ConfigLoader.getProperty("ors.geocode.url");

        String url = baseUrl + "?api_key=" + orsToken + "&text=" + city;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            String errorResponse = response.body() != null ? response.body().string() : "No response body";
            throw new IOException("Unexpected code "+ response +" Response body: "+errorResponse);
        }


        String responseBody = response.body().string();
      //  System.out.println("getCoordinates API Response: " + responseBody);

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray features = jsonResponse.getJSONArray("features");

        if (features.length() == 0) {
            throw new IOException("No coordinates found for city: " + city);
        }

        JSONArray coordinates = features.getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates");

        return coordinates.getDouble(0) + "," + coordinates.getDouble(1); // longitude,latitude
    }
    public static double calculateCO2(double distanceMeters, String method) {
        double distanceKm = Math.max(distanceMeters / 1000.0,0);
        double factor = EmissionFactors.EMISSION_FACTORS_CO2.getOrDefault(method, 0.0);
        return factor * distanceKm;
    }
    private static String getApiToken(){
        String orsToken = System.getenv("ORS_TOKEN");
        return orsToken!=null ? orsToken : ConfigLoader.getProperty("ors.token");
    }
}