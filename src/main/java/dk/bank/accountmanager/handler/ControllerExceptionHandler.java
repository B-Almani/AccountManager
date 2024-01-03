package dk.bank.accountmanager.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import dk.bank.accountmanager.exceptions.InvalidAmountException;

@ControllerAdvice
public class ControllerExceptionHandler {

	 	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
	    @ExceptionHandler(Exception.class)
	    public String defaultExceptionHandler() {
			return "An error occured";
	    }
	 	
	 	@ResponseStatus(HttpStatus.BAD_REQUEST) 
	    @ExceptionHandler(InvalidAmountException.class)
	    public String amountExceptionHandler() {
			return "Invalid amount";
	    }
	 	
		@ResponseStatus(HttpStatus.BAD_REQUEST) 
	    @ExceptionHandler(IllegalArgumentException.class)
	    public String invalidArgumentException() {
			return "Recieved invalid data";
		}
}
