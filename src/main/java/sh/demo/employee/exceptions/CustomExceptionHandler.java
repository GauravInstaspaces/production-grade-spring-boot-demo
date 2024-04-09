package sh.demo.employee.exceptions;

import java.lang.Exception;
import java.lang.Override;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sh.demo.employee.exceptions.EmployeesNotFound;
import sh.demo.employee.exceptions.ParserException;
import sh.demo.employee.exceptions.ValidationException;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = EmployeesNotFound.class)
	public ResponseEntity handleEmployeesNotFound(EmployeesNotFound exception) {
		return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ParserException.class)
	public ResponseEntity parserExceptionHandler(
		ParserException parserException
	) {
		return new ResponseEntity(
			parserException.getMessage(),
			HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(value = ValidationException.class)
	public ResponseEntity validationExceptionHandler(
		ValidationException validationException
	) {
		return new ResponseEntity(
			validationException.getMessage(),
			HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity genericExceptionHandler(Exception exception) {
		log.error("Unhandled exception", exception);
		return new ResponseEntity(
			"Internal Server Error",
			HttpStatus.INTERNAL_SERVER_ERROR
		);
	}

	@Override
	protected ResponseEntity handleMethodArgumentNotValid(
		MethodArgumentNotValidException ex,
		HttpHeaders headers,
		HttpStatusCode status,
		WebRequest request
	) {
		var errors = new HashMap<>();
		for (var err : ex.getBindingResult().getAllErrors()) errors.put(
			((FieldError) err).getField(),
			err.getDefaultMessage()
		);
		return this.handleExceptionInternal(
				ex,
				errors,
				headers,
				status,
				request
			);
	}
}
