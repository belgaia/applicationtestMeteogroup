package exceptions;

/**
 * Exception thrown if something went wrong on persistence process.
 * 
 * @author Isabel Schaefer Batista
 * 
 */
public class PersistException extends Exception {
	
	private static final long serialVersionUID = -3645554430596006177L;

	public PersistException() {}
	
	public PersistException(String message) {
		super(message);
	}
}
