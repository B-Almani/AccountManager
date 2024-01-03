package dk.bank.accountmanager.exceptions;

@SuppressWarnings("serial")
public class InvalidAccountNameException extends Exception {

	 	public InvalidAccountNameException(String errorMessage) {
	        super(errorMessage);
	    }

}
