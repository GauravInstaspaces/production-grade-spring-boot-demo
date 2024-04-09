package sh.demo.employee.repositories;

import sh.demo.employee.entities.PagedResponse;
import sh.demo.employee.models.Employees;

public interface EmployeesRepositoryCustom {
	PagedResponse<Employees> findAllEmployeess(
		String filters,
		String sort,
		Integer limit,
		Integer offset
	);
}
