package org.anolis.networking.request;

public abstract class RequestFormat {

	/**
	 * This must be overriden in order to create a request body 
	 * Note that this happens on an AsyncTask, and it is not safe to interact with the ui thread from here
	 * 
	 * @return String the http read request body
	 */
	String getRequestBody();

	/**
	 * This must be override in order to set a content type into the request
	 *
	 * @return String the content type this request format takes
	 */
	String getContentType();

	/**
	 * This must be override in order to get any relevant url query that needs to be attached to the requested URL
	 *
	 * @return String the query url
	 */
	String getURLQuery();
}