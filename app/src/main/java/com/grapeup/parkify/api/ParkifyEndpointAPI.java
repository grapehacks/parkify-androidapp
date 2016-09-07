package com.grapeup.parkify.api;

/**
 * Parkify endpoint information
 *
 * @author Pavlo Tymchuk
 */
public class ParkifyEndpointAPI implements EndpointAPI {

    public static final String API_URL = "http://krk.grapeup.com:8080"; // "http://krk.grapeup.com:8080";

    @Override
    public String getApiEndpointUrl() {
        return API_URL;
    }
}
