package sh.demo.employee.entities;

import com.querydsl.core.types.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchQuery {

	private String filterName;

	private Operator operation;

	private Object filterValue;
}
