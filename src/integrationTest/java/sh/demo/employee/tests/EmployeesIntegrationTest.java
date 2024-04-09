package sh.demo.employee.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.Exception;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import sh.demo.employee.models.Employees;
import sh.demo.employee.tests.BaseTest;

public class EmployeesIntegrationTest extends BaseTest {

	@Test
	public void shouldCreateEmployeesTest() throws Exception {
		String data = getFileContents("/employees/EmployeesCreate.json");
		HttpEntity entity = new HttpEntity(data, getRestTemplateHeaders());
		assertTrue(
			restTemplate
				.postForEntity("/v1/employeess", entity, Employees.class)
				.getStatusCode()
				.is2xxSuccessful()
		);
	}

	@Test
	public void shouldUpdateEmployeesTest() throws Exception {
		String data = getFileContents("/employees/updateEmployeesCreate.json");
		HttpEntity entity = new HttpEntity(data, getRestTemplateHeaders());
		var createResponse = restTemplate.postForEntity(
			"/v1/employeess",
			entity,
			ResponseEntity.class
		);
		var updateLocationUrl = createResponse
			.getHeaders()
			.get("Location")
			.toString()
			.replaceAll("\\[|\\]", "");

		Thread.sleep(1000L);
		var updateData = getFileContents("/employees/EmployeesUpdate.json");
		var updateEntity = new HttpEntity(updateData, getRestTemplateHeaders());

		Thread.sleep(1000L);
		assertTrue(
			restTemplate
				.exchange(
					updateLocationUrl,
					HttpMethod.PUT,
					updateEntity,
					Employees.class
				)
				.getStatusCode()
				.is2xxSuccessful()
		);
	}

	@Test
	public void shouldGetEmployeesTest() throws Exception {
		String data = getFileContents("/employees/getEmployeesCreate.json");
		HttpEntity entity = new HttpEntity(data, getRestTemplateHeaders());
		var createResponse = restTemplate.postForEntity(
			"/v1/employeess",
			entity,
			ResponseEntity.class
		);
		var reourceIdLocationUrl = createResponse
			.getHeaders()
			.get("Location")
			.toString()
			.replaceAll("\\[|\\]", "");

		Thread.sleep(1000L);
		HttpEntity getEntity = new HttpEntity(getRestTemplateHeaders());

		assertTrue(
			restTemplate
				.exchange(
					reourceIdLocationUrl,
					HttpMethod.GET,
					getEntity,
					Employees.class
				)
				.getStatusCode()
				.is2xxSuccessful()
		);
	}

	@Test
	public void shouldDeleteEmployeesTest() throws Exception {
		String data = getFileContents("/employees/deleteEmployeesCreate.json");
		HttpEntity entity = new HttpEntity(data, getRestTemplateHeaders());
		var createResponse = restTemplate.postForEntity(
			"/v1/employeess",
			entity,
			ResponseEntity.class
		);
		var resourceLocationUrl = createResponse
			.getHeaders()
			.get("Location")
			.toString()
			.replaceAll("\\[|\\]", "");

		Thread.sleep(1000L);
		HttpEntity getEntity = new HttpEntity(getRestTemplateHeaders());

		assertTrue(
			restTemplate
				.exchange(
					resourceLocationUrl,
					HttpMethod.GET,
					getEntity,
					Employees.class
				)
				.getStatusCode()
				.is2xxSuccessful()
		);

		restTemplate.exchange(
			resourceLocationUrl,
			HttpMethod.DELETE,
			getEntity,
			Employees.class
		);

		Thread.sleep(1000L);

		assertTrue(
			restTemplate
				.exchange(
					resourceLocationUrl,
					HttpMethod.GET,
					getEntity,
					Employees.class
				)
				.getStatusCode()
				.is4xxClientError()
		);
	}
}
