package com.grapeup.parkify.api;

/**
 * Parkify endpoint information
 *
 * @author Pavlo Tymchuk
 */
public class ParkifyEndpointAPI implements EndpointAPI {

    public static final String API_URL = "http://";

    @Override
    public String getApiEndpointUrl() {
        return API_URL;
    }
}
