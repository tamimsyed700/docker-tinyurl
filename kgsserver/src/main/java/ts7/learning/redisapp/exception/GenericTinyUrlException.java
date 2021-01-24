package ts7.learning.redisapp.exception;


/**
 * This exception will be raised if the application fails to load the key generation services from NanoID to the flat file.
 * 
 * @author tamim
 *
 */

public class GenericTinyUrlException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GenericTinyUrlException(String message) {
		super(message);
	}

	public GenericTinyUrlException(String message, Throwable t) {
		super(message, t);
	}

}
