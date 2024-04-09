package sh.demo.employee.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.Exception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class HealthControllerTest {

	MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc =
			MockMvcBuilders.standaloneSetup(new HealthController()).build();
	}

	@Test
	public void shouldReturnValidHealthResponse() throws Exception {
		this.mockMvc.perform(get("/health"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("up")));
	}
}
