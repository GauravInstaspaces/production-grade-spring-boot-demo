package sh.demo.employee.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sh.demo.employee.exceptions.ValidationException;
import sh.demo.employee.utils.ValidationHelper;

@Component
public class Validation {

	@Autowired
	ValidationHelper validationHelper;

	public void validateFilters(
		String modelValue,
		String operation,
		String modelName
	) {
		List<String> errorMessages = new ArrayList<>();
		List<String> allowedOperations = validationHelper.allowedFiltersForField
			.getOrDefault(modelName, new HashMap<>())
			.getOrDefault(modelValue, new ArrayList<>());
		if (allowedOperations != null) {
			boolean isFilterAllowed = allowedOperations
				.stream()
				.anyMatch(operation::equalsIgnoreCase);
			if (!isFilterAllowed) {
				errorMessages.add(
					"operation: " + operation + " is not allowed for the field"
				);
			}
		} else {
			errorMessages.add(modelValue + " field is not allowed to filter");
		}

		if (!errorMessages.isEmpty()) {
			throw new ValidationException(
				"Validation failed" + String.join(",", errorMessages)
			);
		}
	}

	public void validateSort(String sortOperation, String modelName) {
		List<String> errorMessages = new ArrayList<>();
		if (
			!validationHelper.allowedSort
				.getOrDefault(modelName, new HashSet<>())
				.contains(sortOperation)
		) {
			errorMessages.add(sortOperation + " is not allowed for sorting");
		}
		if (!errorMessages.isEmpty()) {
			throw new ValidationException(
				"Validation failed" + String.join(",", errorMessages)
			);
		}
	}
}
