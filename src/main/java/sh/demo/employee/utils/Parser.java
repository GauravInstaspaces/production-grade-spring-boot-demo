package sh.demo.employee.utils;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.Object;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import sh.demo.employee.entities.SearchQuery;
import sh.demo.employee.exceptions.ParserException;
import sh.demo.employee.utils.ConvertorDefinition;
import sh.demo.employee.utils.OperationConstants;
import sh.demo.employee.utils.Validation;

@Slf4j
@Component
public class Parser {

	OperationConstants operationConstants = new OperationConstants();

	@Autowired
	Validation validation;

	final Pattern FILTER_SYNTAX_PATTERN = Pattern.compile(
		"([\\w]+):([\\w]+):(.*)"
	);

	@Autowired
	public ConvertorDefinition convertorDefinition;

	public List<SearchQuery> getFilters(String filters, String modelName) {
		if (filters == null || filters.isEmpty()) {
			return new ArrayList<>();
		} else {
			log.info("parsing the filters: {}", filters);
			List<SearchQuery> searchQueries = new ArrayList<>();
			Arrays
				.stream(filters.split(":and:"))
				.forEach(filter -> {
					if (!FILTER_SYNTAX_PATTERN.matcher(filter).matches()) {
						throw new ParserException(
							"invalid syntax for filter " + filter
						);
					}
					Matcher matcher = FILTER_SYNTAX_PATTERN.matcher(filter);
					matcher.find();
					String modelValue = matcher.group(1);
					String op = matcher.group(2);
					Object values = typeCastValue(
						modelValue,
						matcher.group(3),
						modelName
					);
					validation.validateFilters(modelValue, op, modelName);
					searchQueries.add(
						new SearchQuery(
							modelValue,
							operationConstants.operations.get(op.toUpperCase()),
							values
						)
					);
				});

			return searchQueries;
		}
	}

	public List<Sort.Order> getOrderByFields(String sort, String modelName) {
		if (sort == null || sort.isEmpty()) {
			return new ArrayList<>();
		} else {
			List<Order> sorts = new ArrayList<>();
			Arrays
				.stream(sort.split(";"))
				.forEach(it -> {
					String sortObject = "";
					Direction direction = Direction.ASC;
					if (it.charAt(0) == '+' || it.charAt(0) == '-') {
						direction = getSortOperation(it.substring(0, 1));
						sortObject = it.substring(1);
					} else {
						sortObject = it;
					}
					validation.validateSort(sortObject, modelName);
					sorts.add(new Order(direction, sortObject));
				});
			return sorts;
		}
	}

	private Object typeCastValue(
		String modelValue,
		String value,
		String modelName
	) {
		var classDetails = convertorDefinition.classDefinitions.get(modelName);
		if (!classDetails.containsKey(modelValue)) {
			throw new ParserException("Key not found");
		} else {
			var type = classDetails.get(modelValue).getType().getSimpleName();
			Object typedValue = null;
			switch (type) {
				case "Integer" -> typedValue = Integer.valueOf(value);
				case "boolean" -> typedValue = Boolean.valueOf(value);
				case "BigDecimal" -> typedValue = new BigDecimal(value);
				case "Instant" -> typedValue = Instant.parse(value);
				default -> typedValue = value.toString();
			}
			return typedValue;
		}
	}

	public Sort.Direction getSortOperation(String operation) {
		if (operation.equals("+")) {
			return Sort.Direction.ASC;
		} else if (operation.equals("-")) {
			return Sort.Direction.DESC;
		}
		return null;
	}
}
