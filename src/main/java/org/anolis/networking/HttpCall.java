package org.anolis.networking;

import android.os.AsyncTask;
import android.util.Log;

import org.anolis.networking.request.RequestFormat;
import org.anolis.networking.response.ResponseHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;

/**
 * Created by Quixotical on 10/18/15.
 *
 * Class for running http calls
 */
public class HttpCall extends AsyncTask<Void, Void, Boolean>{

    /**
     * The request format for this call
     */
    private final RequestFormat requestFormat;

    /**
     * The response handler for this call
     */
    private final ResponseHandler responseHandler;

    /**
     * Constructs
     * @param request RequestFormat
     */
    public HttpCall(RequestFormat request, ResponseHandler responseHandler) {
        super();

        this.requestFormat = request;
        this.responseHandler = responseHandler;
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
        String requestData = this.requestFormat.getRequestBody();
        String contentType = this.requestFormat.getContentType();

        try {
            URL url = new URL(this.requestFormat.getURL());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try {

                connection.setRequestMethod(this.requestFormat.getMethod());

                connection.setRequestProperty("Accept-Charset", "UTF-8");
                if (contentType != null) {
                    connection.setRequestProperty("Content-Type", contentType);
                }
                for( HashMap.Entry<String, String> entry : this.requestFormat.getHeaders().entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }

                if (requestData != null) {
                    connection.setDoOutput(true);

                    connection.setFixedLengthStreamingMode(requestData.length());

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
                this.responseHandler.parseResponse(response);
            }
            finally {
                connection.disconnect();
            }
        }
        catch(IOException e) {
            Log.e("HTTPCall", e.getMessage(), e);
        }
        return response != null;

    }

    @Override
    public void onPostExecute(Boolean success)
    {
        if(success){
            this.responseHandler.onConnectionSuccess();
        }
        else {
            this.responseHandler.onConnectionFailure();
        }
    }
}
