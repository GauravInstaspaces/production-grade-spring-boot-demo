package sh.demo.employee.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sh.demo.employee.entities.Context;
import sh.demo.employee.entities.PagedResponse;
import sh.demo.employee.models.Employees;
import sh.demo.employee.services.EmployeesService;

@Slf4j
@RestController
@RequestMapping(value = "/v1/employeess")
public class EmployeesQueryController {

	@Autowired
	EmployeesService employeesService;

	@GetMapping(value = "/{employeesId}")
	public Employees get(
		@Parameter(hidden = true) Context context,
		@PathVariable(value = "employeesId") String employeesId
	) {
		log.info("Received a get request for Employees {} ", employeesId);
		Employees existingEmployees = employeesService.get(
			context,
			employeesId
		);
		log.info("Get request for Employees {} is complete ", employeesId);
		return existingEmployees;
	}

	@GetMapping
	public PagedResponse<Employees> selectAll(
		@Parameter(hidden = true) Context context,
		@RequestParam(value = "filters", required = false) String filters,
		@RequestParam(value = "sort", required = false) String sort,
		@RequestParam(value = "limit", required = false) Integer limit,
		@RequestParam(value = "offset", required = false) Integer offset
	) {
		log.info(
			"fetching values with pagelimit: {}, offset: {} for the filters: {} & sort: {}",
			limit,
			offset,
			filters,
			sort
		);
		var employeessPageResponse = employeesService.getAll(
			context,
			filters,
			sort,
			limit,
			offset
		);
		return employeessPageResponse;
	}
}
