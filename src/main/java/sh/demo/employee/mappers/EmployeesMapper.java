package sh.demo.employee.mappers;

import org.springframework.stereotype.Component;
import sh.demo.employee.entities.Context;
import sh.demo.employee.inputs.CreateEmployeesInput;
import sh.demo.employee.inputs.UpdateEmployeesInput;
import sh.demo.employee.models.Employees;

@Component
public class EmployeesMapper {

	public Employees createEmployees(
		Context context,
		CreateEmployeesInput createEmployeesInput,
		String employeesId
	) {
		Employees employees = new Employees();
		employees.setEmployeeid(createEmployeesInput.getEmployeeid());
		employees.setFirstname(createEmployeesInput.getFirstname());
		employees.setLastname(createEmployeesInput.getLastname());
		employees.setEmail(createEmployeesInput.getEmail());
		employees.setDepartment(createEmployeesInput.getDepartment());
		employees.setPosition(createEmployeesInput.getPosition());
		employees.setManager(createEmployeesInput.getManager());
		employees.setHiredate(createEmployeesInput.getHiredate());
		employees.setSalary(createEmployeesInput.getSalary());
		employees.setAddress(createEmployeesInput.getAddress());
		employees.setEmployeesId(employeesId);
		return employees;
	}

	public Employees updateEmployees(
		Context context,
		String employeesId,
		UpdateEmployeesInput updateEmployeesInput,
		Employees existingEmployees
	) {
		existingEmployees.setEmployeeid(updateEmployeesInput.getEmployeeid());
		existingEmployees.setFirstname(updateEmployeesInput.getFirstname());
		existingEmployees.setLastname(updateEmployeesInput.getLastname());
		existingEmployees.setEmail(updateEmployeesInput.getEmail());
		existingEmployees.setDepartment(updateEmployeesInput.getDepartment());
		existingEmployees.setPosition(updateEmployeesInput.getPosition());
		existingEmployees.setManager(updateEmployeesInput.getManager());
		existingEmployees.setHiredate(updateEmployeesInput.getHiredate());
		existingEmployees.setSalary(updateEmployeesInput.getSalary());
		existingEmployees.setAddress(updateEmployeesInput.getAddress());
		return existingEmployees;
	}
}
