package org.anolis.networking.request;

import java.util.HashMap;

/**
 * Created by bryce on 3/10/16.
 *
 * Used for sending get requests
 */
public class GetRequest extends RequestFormat {


    /**
     * Default constructor
     *
     * @param url         The url for this call
     */
    public GetRequest(String url) {
        super(RequestFormat.METHOD_GET, url, null);
    }

    @Override public String getURL() {
        String data = "";

        for( HashMap.Entry<String, Object> entry : super.requestVariables.entrySet()) {
            data+= entry.getKey() + "=" + entry.getValue().toString() + "&";
        }

        if (data.length() > 0) {
            data = data.substring(0, data.length() - 1);

            return super.url + "?" + data;
        }

        return super.url;
    }

    @Override public String getRequestBody() {
        return null;
    }
}
