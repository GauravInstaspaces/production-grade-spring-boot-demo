package sh.demo.employee.repositories;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sh.demo.employee.models.Employees;
import sh.demo.employee.repositories.EmployeesRepositoryCustom;

@Repository
public interface EmployeesRepository
	extends
		PagingAndSortingRepository<Employees, String>,
		CrudRepository<Employees, String>,
		QuerydslPredicateExecutor<Employees>,
		EmployeesRepositoryCustom {}
