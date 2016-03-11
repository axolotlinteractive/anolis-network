package org.anolis.networking.request;

import java.util.HashMap;

/**
 * Created by bryce on 3/10/16.
 *
 * Class used to send HTML form requests
 */
public class FormEncodedRequestFormat extends RequestFormat {
    /**
     * Default constructor
     *
     * @param method      The method for this call
     * @param url         The url for this call
     */
    public FormEncodedRequestFormat(String method, String url) {
        super(method, url,  "application/x-www-form-urlencoded");
    }

    @Override
    public String getRequestBody() {
        String data = "";

        for( HashMap.Entry<String, Object> entry : super.requestVariables.entrySet()) {
            data+= entry.getKey() + "=" + entry.getValue().toString() + "&";
        }

        if (data.length() > 0)
            data = data.substring(0, data.length() - 1);

        return data;
    }
}
