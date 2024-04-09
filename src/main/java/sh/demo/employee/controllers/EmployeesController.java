package sh.demo.employee.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sh.demo.employee.entities.Context;
import sh.demo.employee.inputs.CreateEmployeesInput;
import sh.demo.employee.inputs.UpdateEmployeesInput;
import sh.demo.employee.models.Employees;
import sh.demo.employee.services.EmployeesService;

@Slf4j
@RestController
@RequestMapping(value = "/v1/employeess")
public class EmployeesController {

	@Autowired
	EmployeesService employeesService;

	@PostMapping
	public ResponseEntity create(
		@Valid @Parameter(hidden = true) Context context,
		@Valid @RequestBody CreateEmployeesInput createEmployeesInput
	) {
		log.info("Received a new create request");
		var id = Employees.getNewEmployeesId();
		employeesService.create(context, createEmployeesInput, id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Location", "/v1/employeess/" + id);
		log.info("Create request for Employees - {} is complete", id);
		return new ResponseEntity(null, responseHeaders, HttpStatus.OK);
	}

	@PutMapping(value = "/{employeesId}")
	public ResponseEntity update(
		@Valid @Parameter(hidden = true) Context context,
		@PathVariable(value = "employeesId") String employeesId,
		@Valid @RequestBody UpdateEmployeesInput updateEmployeesInput
	) {
		log.info("Received a update request for Employees {} ", employeesId);
		employeesService.update(context, employeesId, updateEmployeesInput);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Location", "/v1/employeess/" + employeesId);
		log.info("Update request for Employees {} is complete", employeesId);
		return new ResponseEntity(null, responseHeaders, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{employeesId}")
	public void delete(
		@Parameter(hidden = true) Context context,
		@PathVariable(value = "employeesId") String employeesId
	) {
		log.info("Received a delete request for Employees {} ", employeesId);
		employeesService.delete(context, employeesId);
		log.info("Delete request completed for Employees {} ", employeesId);
	}
}
