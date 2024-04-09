package sh.demo.employee.services;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.demo.employee.entities.Context;
import sh.demo.employee.entities.PagedResponse;
import sh.demo.employee.events.EmployeesUpdateAccepted;
import sh.demo.employee.events.EmployeesUpdateAcceptedPayload;
import sh.demo.employee.events.EventPublisher;
import sh.demo.employee.exceptions.EmployeesNotFound;
import sh.demo.employee.inputs.CreateEmployeesInput;
import sh.demo.employee.inputs.UpdateEmployeesInput;
import sh.demo.employee.mappers.EmployeesMapper;
import sh.demo.employee.models.Employees;
import sh.demo.employee.repositories.EmployeesRepository;

@Service
@Slf4j
public class EmployeesService {

	@Autowired
	EventPublisher eventPublisher;

	@Autowired
	EmployeesRepository employeesRepository;

	@Autowired
	EmployeesMapper employeesMapper;

	public Employees create(
		Context context,
		CreateEmployeesInput createEmployeesInput,
		String employeesId
	) {
		Employees employees = employeesMapper.createEmployees(
			context,
			createEmployeesInput,
			employeesId
		);
		Employees createdEmployees = employeesRepository.save(employees);
		return createdEmployees;
	}

	public void update(
		Context context,
		String employeesId,
		UpdateEmployeesInput updateEmployeesInput
	) {
		Optional<Employees> existingEmployeesData = employeesRepository.findById(
			employeesId
		);

		if (existingEmployeesData.isEmpty()) {
			throw new EmployeesNotFound();
		}
		eventPublisher.sendEmployeesEvent(
			new EmployeesUpdateAccepted(
				new EmployeesUpdateAcceptedPayload(
					context,
					employeesId,
					updateEmployeesInput
				)
			),
			employeesId
		);
	}

	public Employees handleEmployeesUpdateAccepted(
		Context context,
		String employeesId,
		UpdateEmployeesInput updateEmployeesInput
	) {
		Optional<Employees> existingEmployeesData = employeesRepository.findById(
			employeesId
		);

		if (existingEmployeesData.isEmpty()) {
			throw new EmployeesNotFound();
		}
		Employees updatedEmployees = employeesMapper.updateEmployees(
			context,
			employeesId,
			updateEmployeesInput,
			existingEmployeesData.get()
		);
		Employees savedEmployees = employeesRepository.save(updatedEmployees);
		return savedEmployees;
	}

	public Employees get(Context context, String employeesId) {
		Optional<Employees> existingEmployeesData = employeesRepository.findById(
			employeesId
		);
		if (existingEmployeesData.isEmpty()) {
			throw new EmployeesNotFound();
		}
		return existingEmployeesData.get();
	}

	public PagedResponse<Employees> getAll(
		Context context,
		String filters,
		String sort,
		Integer limit,
		Integer offset
	) {
		log.info("inside findAll service method");
		return employeesRepository.findAllEmployeess(
			filters,
			sort,
			limit,
			offset
		);
	}

	public void delete(Context context, String employeesId) {
		Optional<Employees> existingEmployeesData = employeesRepository.findById(
			employeesId
		);

		if (existingEmployeesData.isEmpty()) {
			throw new EmployeesNotFound();
		}
		employeesRepository.deleteById(employeesId);
	}
}
