package sh.demo.employee.configurations;

import java.lang.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sh.demo.employee.configurations.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	@Autowired
	JwtAuthenticationFilter filter;

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.addFilterBefore(
				filter,
				UsernamePasswordAuthenticationFilter.class
			);

		return http.build();
	}
}
