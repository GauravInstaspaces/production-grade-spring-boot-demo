package sh.demo.employee.events;

import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.cloud.stream.function.StreamBridge;
import sh.demo.employee.BaseTest;
import sh.demo.employee.DummyBaseEvent;
import sh.demo.employee.entities.Context;
import sh.demo.employee.entities.PagedResponse;
import sh.demo.employee.events.BaseEvent;
import sh.demo.employee.events.EventPublisher;
import sh.demo.employee.inputs.CreateEmployeesInput;
import sh.demo.employee.inputs.UpdateEmployeesInput;
import sh.demo.employee.models.Employees;

public class EventPublisherTest extends BaseTest {

	@Mock
	StreamBridge streamBridge;

	@InjectMocks
	EventPublisher eventPublisher;

	@Test
	public void shouldTestSendEmployeesEvent() throws IOException {
		// TODO : validate mocks, values and return type asserts
		Mockito.when(streamBridge.send(any(), any())).thenReturn(false);
		eventPublisher.sendEmployeesEvent(
			getResource("event.json", DummyBaseEvent.class),
			"defaultString"
		);
		Mockito.verify(streamBridge, Mockito.times(1)).send(any(), any());
	}
}
