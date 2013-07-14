package exceptions;

/**
 * Thrown if a searched person was not found.
 * 
 * @author Isabel Schaefer Batista
 * 
 */
public class PersonNotFoundException extends Exception {
	
	private static final long serialVersionUID = -8331082275983402243L;
	
	public PersonNotFoundException() {}
	
	public PersonNotFoundException(String message) {
		super(message);
	}
}
