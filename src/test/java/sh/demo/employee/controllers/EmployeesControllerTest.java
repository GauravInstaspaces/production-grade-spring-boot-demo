package sh.demo.employee.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import sh.demo.employee.controllers.EmployeesController;
import sh.demo.employee.models.Employees;
import sh.demo.employee.services.EmployeesService;

public class EmployeesControllerTest extends BaseTest {

	MockMvc mockMvc;

	@Mock
	EmployeesService employeesService;

	@InjectMocks
	EmployeesController employeesController;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(employeesController).build();
	}

	@Test
	public void shouldPerformCreateTest() throws Exception {
		var data = getResource("Employees.json", Employees.class);
		Mockito
			.when(employeesService.create(any(), any(), any()))
			.thenReturn(data);
		mockMvc
			.perform(
				post("/v1/employeess")
					.contentType(MediaType.APPLICATION_JSON)
					.content(getFileContents("createEmployeesInput.json"))
			)
			.andExpect(status().is2xxSuccessful());
		// verify(employeesService, Mockito.times(1)).create(any(),any(), any());

	}

	@Test
	public void shouldPerformUpdateTest() throws Exception {
		var data = getResource("Employees.json", Employees.class);

		Mockito
			.when(employeesService.update(any(), any(), any()))
			.thenReturn(data);
		mockMvc
			.perform(
				put("/v1/employeess/employeesId")
					.contentType(MediaType.APPLICATION_JSON)
					.content(getFileContents("updateEmployeesInput.json"))
			)
			.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldPerformDeleteTest() throws Exception {
		Mockito.doNothing().when(employeesService).delete(any(), any());
		mockMvc
			.perform(
				delete("/v1/employeess/employeesId")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().is2xxSuccessful());
	}
}
