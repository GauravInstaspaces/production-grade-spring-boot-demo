package sh.demo.employee.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import sh.demo.employee.models.Employees;

@Component
@Slf4j
public class JwtUtils {

	@Autowired
	public ObjectMapper objectMapper;

	@Autowired
	public JwtDecoder jwtDecoder;

	public UsernamePasswordAuthenticationToken validateJwt(String token) {
		UsernamePasswordAuthenticationToken auth = null;
		try {
			var decodedToken = jwtDecoder.decode(token);
			var jwtObject = objectMapper.convertValue(
				decodedToken.getClaims(),
				Employees.class
			);
			List<String> authoritiesData = List.of();

			//TODO Add Claims from JWT for authorization

			var authorities = authoritiesData
				.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
			/**
			 *  TODO
			 *  Update userName from deserialized jwt object
			 *  currently subject is used as userName
			 * */
			var user = new User(decodedToken.getSubject(), "", authorities);
			auth = new UsernamePasswordAuthenticationToken(user, jwtObject);
		} catch (Exception e) {
			log.error("Failed to validate token", e);
		}
		return auth;
	}
}
