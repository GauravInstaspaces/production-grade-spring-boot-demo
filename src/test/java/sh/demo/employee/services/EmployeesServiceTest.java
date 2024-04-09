package sh.demo.employee.services;

import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import sh.demo.employee.BaseTest;
import sh.demo.employee.entities.Context;
import sh.demo.employee.entities.PagedResponse;
import sh.demo.employee.events.EventPublisher;
import sh.demo.employee.inputs.CreateEmployeesInput;
import sh.demo.employee.inputs.UpdateEmployeesInput;
import sh.demo.employee.mappers.EmployeesMapper;
import sh.demo.employee.models.Employees;
import sh.demo.employee.repositories.EmployeesRepository;
import sh.demo.employee.services.EmployeesService;

public class EmployeesServiceTest extends BaseTest {

	@Mock
	EventPublisher eventPublisher;

	@Mock
	EmployeesRepository employeesRepository;

	@Mock
	EmployeesMapper employeesMapper;

	@InjectMocks
	EmployeesService employeesService;

	@Test
	public void shouldTestCreate() throws IOException {
		// TODO : validate mocks, values and return type asserts
		Mockito
			.when(employeesRepository.save(any()))
			.thenReturn(getResource("Employees.json", Employees.class));
		Mockito
			.when(employeesMapper.createEmployees(any(), any(), any()))
			.thenCallRealMethod();
		var actual = employeesService.create(
			getResource("context.json", Context.class),
			getResource(
				"createEmployeesInput.json",
				CreateEmployeesInput.class
			),
			"defaultString"
		);
		expect.serializer("json").toMatchSnapshot(actual);
		Mockito.verify(employeesRepository, Mockito.times(1)).save(any());
		Mockito
			.verify(employeesMapper, Mockito.times(1))
			.createEmployees(any(), any(), any());
	}

	@Test
	public void shouldTestUpdate() throws IOException {
		// TODO : validate mocks, values and return type asserts
		Mockito
			.doNothing()
			.when(eventPublisher)
			.sendEmployeesEvent(any(), any());
		Mockito
			.when(employeesRepository.findById(any()))
			.thenReturn(
				Optional.of(
					getResource("OptionalEmployees.json", Employees.class)
				)
			);
		employeesService.update(
			getResource("context.json", Context.class),
			"defaultString",
			getResource("updateEmployeesInput.json", UpdateEmployeesInput.class)
		);
		Mockito
			.verify(eventPublisher, Mockito.times(1))
			.sendEmployeesEvent(any(), any());
		Mockito.verify(employeesRepository, Mockito.times(1)).findById(any());
	}

	@Test
	public void shouldTestHandleEmployeesUpdateAccepted() throws IOException {
		// TODO : validate mocks, values and return type asserts
		Mockito
			.when(employeesRepository.findById(any()))
			.thenReturn(
				Optional.of(
					getResource("OptionalEmployees.json", Employees.class)
				)
			);
		Mockito
			.when(employeesMapper.updateEmployees(any(), any(), any(), any()))
			.thenCallRealMethod();
		var actual = employeesService.handleEmployeesUpdateAccepted(
			getResource("context.json", Context.class),
			"defaultString",
			getResource("updateEmployeesInput.json", UpdateEmployeesInput.class)
		);
		expect.serializer("json").toMatchSnapshot(actual);
		Mockito.verify(employeesRepository, Mockito.times(1)).findById(any());
		Mockito
			.verify(employeesMapper, Mockito.times(1))
			.updateEmployees(any(), any(), any(), any());
	}

	@Test
	public void shouldTestGet() throws IOException {
		// TODO : validate mocks, values and return type asserts
		Mockito
			.when(employeesRepository.findById(any()))
			.thenReturn(
				Optional.of(
					getResource("OptionalEmployees.json", Employees.class)
				)
			);
		var actual = employeesService.get(
			getResource("context.json", Context.class),
			"defaultString"
		);
		expect.serializer("json").toMatchSnapshot(actual);
		Mockito.verify(employeesRepository, Mockito.times(1)).findById(any());
	}

	@Test
	public void shouldTestGetAll() throws IOException {
		// TODO : validate mocks, values and return type asserts
		Mockito
			.when(
				employeesRepository.findAllEmployeess(
					any(),
					any(),
					any(),
					any()
				)
			)
			.thenReturn(getResource("PagedResponse.json", PagedResponse.class));
		var actual = employeesService.getAll(
			getResource("context.json", Context.class),
			"defaultString",
			"defaultString",
			123,
			123
		);
		expect.serializer("json").toMatchSnapshot(actual);
		Mockito
			.verify(employeesRepository, Mockito.times(1))
			.findAllEmployeess(any(), any(), any(), any());
	}

	@Test
	public void shouldTestDelete() throws IOException {
		// TODO : validate mocks, values and return type asserts
		Mockito
			.when(employeesRepository.findById(any()))
			.thenReturn(
				Optional.of(
					getResource("OptionalEmployees.json", Employees.class)
				)
			);
		employeesService.delete(
			getResource("context.json", Context.class),
			"defaultString"
		);
		Mockito.verify(employeesRepository, Mockito.times(1)).findById(any());
	}
}
