package sh.demo.employee.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import sh.demo.employee.tests.BaseTest;

public class HealthTest extends BaseTest {

	@Test
	public void applicationHealth() {
		assertTrue(
			restTemplate
				.getForEntity("/health", String.class)
				.getStatusCode()
				.is2xxSuccessful()
		);
	}
}
