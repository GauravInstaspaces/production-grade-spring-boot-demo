package sh.demo.employee.events;

import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import sh.demo.employee.BaseTest;
import sh.demo.employee.DummyBaseEvent;
import sh.demo.employee.entities.Context;
import sh.demo.employee.entities.PagedResponse;
import sh.demo.employee.events.BaseEvent;
import sh.demo.employee.events.EmployeesUpdateAccepted;
import sh.demo.employee.events.EventConsumer;
import sh.demo.employee.inputs.CreateEmployeesInput;
import sh.demo.employee.inputs.UpdateEmployeesInput;
import sh.demo.employee.models.Employees;
import sh.demo.employee.services.EmployeesService;

public class EventConsumerTest extends BaseTest {

	@Mock
	EmployeesService employeesService;

	@InjectMocks
	EventConsumer eventConsumer;

	@Test
	public void shouldTestEmployeesUpdateAccepted() throws IOException {
		// TODO : validate mocks, values and return type asserts
		Mockito
			.when(
				employeesService.handleEmployeesUpdateAccepted(
					any(),
					any(),
					any()
				)
			)
			.thenReturn(getResource("Employees.json", Employees.class));
		var actualLambda = eventConsumer.employeesUpdateAccepted();
		actualLambda.accept(
			getResource(
				"EmployeesUpdateAccepted.json",
				EmployeesUpdateAccepted.class
			)
		);
		Mockito
			.verify(employeesService, Mockito.times(1))
			.handleEmployeesUpdateAccepted(any(), any(), any());
	}
}
