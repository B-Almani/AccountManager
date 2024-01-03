package dk.bank.accountmanager.exceptions;

@SuppressWarnings("serial")
public class InvalidAmountException extends Exception {

	public InvalidAmountException(String errorMessage) {
        super(errorMessage);
    }
}
