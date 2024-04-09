package sh.demo.employee.exceptions;

import java.lang.Throwable;
import org.springframework.web.bind.annotation.ResponseStatus;
import sh.demo.employee.exceptions.EmployeeCustomException;

@ResponseStatus
public class ValidationException extends EmployeeCustomException {

	public ValidationException() {
		super();
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable t) {
		super(message, t);
	}
}
