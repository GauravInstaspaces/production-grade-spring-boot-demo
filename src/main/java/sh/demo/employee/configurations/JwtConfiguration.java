package sh.demo.employee.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;

public class JwtConfiguration {

	@Value(value = "${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuerUrl;

	@Bean
	public JwtDecoder jwtDecoder() {
		return JwtDecoders.fromIssuerLocation(issuerUrl);
	}
}
