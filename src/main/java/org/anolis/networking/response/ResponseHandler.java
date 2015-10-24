package org.anolis.networking.response;

/**
 * Created by Quixotical on 10/18/15.
 */
public interface ResponseHandler {
    /**
     * method that will be called when there is a successful connection to the server
     */
    void onConnectionSuccess();
    /**
     * method that will be called when there is not a successful connection to the server
     */
    void onConnectionFailure();
    /**
     * method that will parse the data we get back from the server
     */
    void parseResponse(String response);
}
