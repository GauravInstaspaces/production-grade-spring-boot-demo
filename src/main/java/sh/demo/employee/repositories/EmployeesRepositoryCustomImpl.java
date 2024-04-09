package sh.demo.employee.repositories;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import sh.demo.employee.entities.PageInfo;
import sh.demo.employee.entities.PagedResponse;
import sh.demo.employee.entities.SearchQuery;
import sh.demo.employee.exceptions.EmployeesNotFound;
import sh.demo.employee.models.Employees;
import sh.demo.employee.repositories.EmployeesRepository;
import sh.demo.employee.repositories.EmployeesRepositoryCustom;
import sh.demo.employee.utils.Parser;

@Repository
public class EmployeesRepositoryCustomImpl
	implements EmployeesRepositoryCustom {

	@Autowired
	Parser parser;

	@Value(value = "${default.limit}")
	private Integer defaultLimit;

	@Value(value = "${default.offset}")
	private Integer defaultOffSet;

	@Autowired
	@Lazy
	EmployeesRepository employeesRepository;

	public PagedResponse<Employees> findAllEmployeess(
		String filters,
		String sort,
		Integer limit,
		Integer offset
	) {
		List<SearchQuery> searchQueries = parser.getFilters(
			filters,
			"employees"
		);
		List<Sort.Order> sortorder = parser.getOrderByFields(sort, "employees");
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		for (SearchQuery query : searchQueries) {
			booleanBuilder.and(
				Expressions.predicate(
					query.getOperation(),
					Expressions.stringPath(query.getFilterName()),
					Expressions.constant(query.getFilterValue())
				)
			);
		}
		try {
			if (limit == null) {
				limit = defaultLimit;
			}
			if (offset == null) {
				offset = defaultOffSet;
			}
			var pageRequest = PageRequest.of(offset, limit, Sort.by(sortorder));
			var employeess = employeesRepository.findAll(
				booleanBuilder,
				pageRequest
			);
			var pageResponse = new PagedResponse<Employees>();
			pageResponse.setResults(
				employeess.get().collect(Collectors.toList())
			);
			var pageInfo = new PageInfo();
			pageInfo.setTotal(employeess.getTotalElements());
			pageInfo.setLimit(limit);
			pageInfo.setOffset(offset);
			pageResponse.setPageInfo(pageInfo);
			return pageResponse;
		} catch (Exception e) {
			throw new EmployeesNotFound(
				"failed to fetch orders with filters " +
				filters +
				" and order by: " +
				sort
			);
		}
	}
}
