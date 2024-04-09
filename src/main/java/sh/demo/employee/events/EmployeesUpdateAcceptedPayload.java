package sh.demo.employee.events;

import lombok.Getter;
import lombok.Setter;
import sh.demo.employee.entities.Context;
import sh.demo.employee.inputs.UpdateEmployeesInput;

@Getter
@Setter
public class EmployeesUpdateAcceptedPayload {

	private Context context;

	private String employeesId;

	private UpdateEmployeesInput updateEmployeesInput;

	public EmployeesUpdateAcceptedPayload(
		Context context,
		String employeesId,
		UpdateEmployeesInput updateEmployeesInput
	) {
		this.context = context;
		this.employeesId = employeesId;
		this.updateEmployeesInput = updateEmployeesInput;
	}
}
