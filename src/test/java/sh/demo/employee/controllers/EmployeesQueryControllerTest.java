package sh.demo.employee.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.Exception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sh.demo.employee.BaseTest;
import sh.demo.employee.controllers.EmployeesQueryController;
import sh.demo.employee.entities.PagedResponse;
import sh.demo.employee.models.Employees;
import sh.demo.employee.services.EmployeesService;

public class EmployeesQueryControllerTest extends BaseTest {

	MockMvc mockMvc;

	@Mock
	EmployeesService employeesService;

	@InjectMocks
	EmployeesQueryController employeesQueryController;

	@BeforeEach
	public void setup() {
		mockMvc =
			MockMvcBuilders.standaloneSetup(employeesQueryController).build();
	}

	@Test
	public void shouldPerformGetTest() throws Exception {
		var data = getResource("Employees.json", Employees.class);
		Mockito.when((employeesService).get(any(), any())).thenReturn(data);
		mockMvc
			.perform(
				get("/v1/employeess/employeesId")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk());
	}

	@Test
	public void shouldPerformSelectAllTest() throws Exception {
		PagedResponse<Employees> mockResponse = new PagedResponse<>();
		Mockito
			.when(employeesService.getAll(any(), any(), any(), any(), any()))
			.thenReturn(mockResponse);
		mockMvc
			.perform(
				get("/v1/employeess").contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk());
	}
}
