package sh.demo.employee.exceptions;

import java.lang.Throwable;
import org.springframework.web.bind.annotation.ResponseStatus;
import sh.demo.employee.exceptions.EmployeeCustomException;

@ResponseStatus
public class ParserException extends EmployeeCustomException {

	public ParserException() {
		super();
	}

	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, Throwable t) {
		super(message, t);
	}
}
