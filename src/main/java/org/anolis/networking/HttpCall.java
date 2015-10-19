package org.anolis.networking;

import android.os.AsyncTask;
import android.util.Log;

import org.anolis.networking.response.ResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Quixotical on 10/18/15.
 */
public class HttpCall extends AsyncTask<Void, Void, Boolean>{
    /**
     * The int that represents a GET request.
     */
    protected static final String REQUEST_METHOD_GET = "GET";
    /**
     * The int that represents a POST request.
     */
    protected static final String REQUEST_METHOD_POST = "POST";
    /**
     * The int that represents a PUT request.
     */
    protected static final String REQUEST_METHOD_PUT = "PUT";
    /**
     * The int that represents a DELETE request.
     */
    protected static final String REQUEST_METHOD_DELETE = "DELETE";
    /**
     * int = method
     */
    private String mMethod;
    /**
     * String = url path
     */
    private String mUrl;
    /**
     * String = authentication
     */
    private String mAuthentication;
    /**
     * ResponseHandler = the response handler
     */
    private ResponseHandler mResponseHandler;
    /**
     * Constructs
     * @param method
     * @param url
     */
    protected HttpCall(String url, String method, ResponseHandler responseHandler)
    {
        super();
        this.mMethod = method;
        this.mUrl = url;
        this.mResponseHandler = responseHandler;
    }
    public void setAuthenticaion(String type, String value){

        this.mAuthentication = type.concat(value);
    }
    /**
     * Retrieves a list of parameters and returns it
     * @return ArrayList
     */
    protected HashMap<String, String> getParams(){ return new HashMap<>(); }

    /**
     * creates a url encoded string of available parameters
     * @param params HashMap all parameters that need to be encoded
     * @return String url encoded string
     */
    private String getRequestData(HashMap<String, String> params) {

        String data = "";

        for( HashMap.Entry<String, String> entry : params.entrySet()) {
            data+= entry.getKey() + "=" + entry.getValue() + "&";
        }

        if (data.length() > 0)
            data = data.substring(0, data.length() - 1);

        return data;
    }

    /**
     * Determines the request method, and creates the object required to run the call.
     * When ready, it calls processRequest and returns the result from that method.
     * @param params the params that are required to run this call
     */
    @Override
    protected final Boolean doInBackground(Void... params)
    {
        String response = null;
        String requestData = this.getRequestData(this.getParams());

        if(this.mMethod.equals(HttpCall.REQUEST_METHOD_GET)) {
            mUrl += "?" + requestData;
        }

        try {
            URL url = new URL(mUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try {

                connection.setRequestMethod(this.mMethod);
                //TODO make authentication generic
                connection.setRequestProperty("Authorization", mAuthentication);
                connection.setRequestProperty("Accept-Charset", "UTF-8");

                if (!this.mMethod.equals(HttpCall.REQUEST_METHOD_GET)) {
                    connection.setDoOutput(true);

                    connection.setFixedLengthStreamingMode(requestData.length());

                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(requestData);
                    wr.flush();
                    wr.close();
                }

                connection.connect();

                response = "";
                BufferedReader in;
                if (connection.getResponseCode() == 200) {
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                }
                else {
                    in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                }

                for (String line; (line = in.readLine()) != null; ) {
                    response += line;
                }
                in.close();
                mResponseHandler.responseParser(response);
            }
            finally {
                connection.disconnect();
            }
        }
        catch(IOException e) {
            Log.e("baseCall", e.getMessage(), e);
        }
        return response != null;

    }

    @Override
    public void onPostExecute(Boolean responseData)
    {
        if(responseData){
            mResponseHandler.onConnectionSuccess();
        }
        else {
            mResponseHandler.onConnectionFailure();
        }
    }
}
