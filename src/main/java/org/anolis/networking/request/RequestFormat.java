package org.anolis.networking.request;

public interface RequestFormat {

	/**
	 * This must be overriden in order to create a request body 
	 * Note that this happens on an AsyncTask, and it is not safe to interact with the ui thread from here
	 * 
	 * @return String the http read request body
	 */
	public String getRequestBody();
}