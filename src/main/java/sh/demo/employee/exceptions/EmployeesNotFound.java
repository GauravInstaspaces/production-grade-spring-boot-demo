package sh.demo.employee.exceptions;

import java.lang.Throwable;
import org.springframework.web.bind.annotation.ResponseStatus;
import sh.demo.employee.exceptions.EmployeeCustomException;

@ResponseStatus
public class EmployeesNotFound extends EmployeeCustomException {

	public EmployeesNotFound() {
		super();
	}

	public EmployeesNotFound(String message) {
		super(message);
	}

	public EmployeesNotFound(String message, Throwable t) {
		super(message, t);
	}
}
