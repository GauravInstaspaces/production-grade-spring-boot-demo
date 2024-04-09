package sh.demo.employee.exceptions;

import java.lang.RuntimeException;
import java.lang.Throwable;

public class EmployeeCustomException extends RuntimeException {

	public EmployeeCustomException() {
		super();
	}

	public EmployeeCustomException(String message) {
		super(message);
	}

	public EmployeeCustomException(String message, Throwable t) {
		super(message, t);
	}
}
