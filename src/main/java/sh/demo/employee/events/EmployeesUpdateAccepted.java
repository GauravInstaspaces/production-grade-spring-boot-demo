package sh.demo.employee.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sh.demo.employee.events.BaseEvent;
import sh.demo.employee.events.EmployeesUpdateAcceptedPayload;

@Getter
@Setter
@NoArgsConstructor
public class EmployeesUpdateAccepted
	extends BaseEvent<EmployeesUpdateAcceptedPayload> {

	public EmployeesUpdateAccepted(EmployeesUpdateAcceptedPayload data) {
		super(data);
		this.setEventType("sh.demo.employee.events.EmployeesUpdateAccepted");
	}
}
