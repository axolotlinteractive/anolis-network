package org.anolis.networking.response;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Quixotical on 10/18/15.
 *
 */
public abstract class JSONResponseHandler implements ResponseHandler {

    /**
     * JSONObject JSONResponse = the converted parseResponse String stored as a JSONObject
     */
    private JSONObject response;

    /**
     * The error that was generated when we attempted to parse the body
     */
    private JSONException error;


    @Override public void onConnectionSuccess() {
        if (this.error == null) {
            this.onCallSuccess(this.response);
        }
        else {
            this.onCallFailure(this.error);
        }
    }

    /**
     *
     * @param response the raw response received
     */
    @Override public final void parseResponse(String response){
        try {
            this.response = new JSONObject(response);
            this.onBackgroundSuccess(this.response);
        }
        catch(JSONException e) {
            Log.e("JSONResponseHandler", e.getMessage());
            this.error = e;
        }
    }

    /**
     * Called in the background in order to dispatch the result on the thread that made the call
     * @param response the parsed response
     */
    public abstract void onBackgroundSuccess(JSONObject response);

    /**
     * Called after the call has returned to the UI Thread
     * @param response the parsed response
     */
    public abstract void onCallSuccess(JSONObject response);

    /**
     * Called in the background in order to dispatch
     * @param responseException the error that was received
     */
    public abstract void onCallFailure(JSONException responseException);
}
