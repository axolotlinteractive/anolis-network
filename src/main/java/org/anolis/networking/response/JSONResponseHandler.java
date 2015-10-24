package org.anolis.networking.response;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Quixotical on 10/18/15.
 */
public abstract class JSONResponseHandler implements ResponseHandler{
    /**
     * JSONObject JSONResponse = the converted parseResponse String stored as a JSONObject
     */
    private JSONObject JSONResponse;

    public abstract void onResponseSuccess(JSONObject response);

    @Override
    public void onConnectionSuccess() {
        responseData(JSONResponse);
    }

    @Override
    public void parseResponse(String response){
        try{
            JSONResponse = new JSONObject(response);
        }catch(JSONException e){
            Log.e("JSONResponseHandler", e.getMessage());
        }
    }
}
