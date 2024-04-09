package sh.demo.employee.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

	@Bean
	public OpenAPI opeAPIConfiguration() {
		return new OpenAPI()
			.info(new Info().title("employee"))
			.components(
				new Components()
					.addSecuritySchemes(
						"Bearer JWT Authentication Header",
						new SecurityScheme()
							.type(SecurityScheme.Type.HTTP)
							.in(SecurityScheme.In.HEADER)
							.bearerFormat("JWT")
							.scheme("bearer")
					)
			)
			.addSecurityItem(
				new SecurityRequirement()
					.addList("Bearer JWT Authentication Header")
			);
	}
}
