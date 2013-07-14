package exceptions;

public class PersonNotFoundException extends Exception {
	
	private static final long serialVersionUID = -8331082275983402243L;
	private String message;
	private int errorCode;
	
	public PersonNotFoundException() {}
	
	public PersonNotFoundException(String message, int errorCode) {
		this.message = message;
		this.errorCode = errorCode;
	}
}
