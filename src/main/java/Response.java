/**
 * 
 */

/**
 * @author Francesco Carratta
 * Response is a class that link message to their response
 */
public class Response {
	
	/**
	 * message is a static text that client can send
	 */
	private String message;
	/**
	 * response is the text send to the client link to the message
	 */
	private String response;
	
	/**
	 * Constructor
	 * @param message
	 * @param response
	 */
	Response(String message, String response){
		this.message = message;
		this.response = response;
	}
	
	/**
	 * a simple get function
	 * @return the response
	 */
	public String getResponse() {
		return this.response;
	}
	
	/**
	 * set response function
	 * @param response 
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	
	/**
	 * set message function
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	

	/**
	 * Check if message is equal to this.message
	 * @param message is a String
	 * @return true if this.message is equal to message
	 */
	public boolean isSimilarMessage(String message) {
		return this.message.equals(message);
	}
	
	/**
	 * Show on terminal the object
	 */
	public void show() {
		System.out.println("[info] message: " + this.message + " --> response: " + this.response);
	}
	
	
	
}
