package org.anolis.networking.request;

import java.util.HashMap;

public abstract class RequestFormat {

	/**
	 * The int that represents a GET request.
	 */
	public static final String METHOD_GET = "GET";

	/**
	 * The int that represents a POST request.
	 */
	public static final String METHOD_POST = "POST";

	/**
	 * The int that represents a PUT request.
	 */
	public static final String METHOD_PUT = "PUT";

	/**
	 * The int that represents a DELETE request.
	 */
	public static final String METHOD_DELETE = "DELETE";

	/**
	 * The HTTP method for this request
	 */
	private String method;

	/**
	 * The url for this request
	 */
	private String url;

	/**
	 * The headers the user has specified
	 */
	private HashMap<String, String> headers = new HashMap<>();

	/**
	 * The data for this request
	 */
	protected HashMap<String, Object> requestVariables = new HashMap<>();

	/**
	 * Adds a new request variable to the call
	 * @param key the name of the variable
	 * @param variable the value of the variable
	 */
	public void addRequestVariable(String key, Object variable) {
		this.requestVariables.put(key, variable);
	}

	/**
	 * Use this to set any headers needed for the call
	 * @param name the name of the header
	 * @param value the value of the header
	 */
	public void addHeader(String name, String value) {

		this.headers.put(name, value);
	}

	/**
	 * @return the request method for this call
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @return String the url for this request
	 */
	public String getURL() {
		return url;
	}

	/**
	 *
	 * @return the headers for this call
	 */
	public HashMap<String, String> getHeaders() {
		return headers;
	}

	/**
	 * This must be overriden in order to create a request body 
	 * Note that this happens on an AsyncTask, and it is not safe to interact with the ui thread from here
	 * 
	 * @return String the http read request body or null if there is none
	 */
	public abstract String getRequestBody();

	/**
	 * This must be override in order to set a content type into the request
	 *
	 * @return String the content type this request format takes
	 */
	public abstract String getContentType();
}