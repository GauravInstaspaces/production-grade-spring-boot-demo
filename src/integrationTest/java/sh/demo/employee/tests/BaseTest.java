package sh.demo.employee.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.salesforce.kafka.test.junit5.SharedKafkaTestResource;
import com.salesforce.kafka.test.listeners.PlainListener;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(value = SpringExtension.class)
@Testcontainers
@DirtiesContext
public class BaseTest {

	@Container
	static KeycloakContainer keycloak = new KeycloakContainer(
		"quay.io/keycloak/keycloak:22.0"
	)
		.withRealmImportFile("keycloak_realm.json")
		.withEnv("DB_VENDOR", "h2");

	@Autowired
	TestRestTemplate restTemplate;

	static boolean firstTestDelayMet = false;

	@RegisterExtension
	static final SharedKafkaTestResource kafka = new SharedKafkaTestResource()
		.withBrokers(1)
		.registerListener(new PlainListener().onPorts(29092));

	@Container
	static MySQLContainer dbContainer = new MySQLContainer<>(
		DockerImageName.parse("mysql:latest")
	);

	protected String getToken() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.put("grant_type", Collections.singletonList("client_credentials"));
		map.put("client_id", Collections.singletonList("library-client"));
		map.put(
			"client_secret",
			Collections.singletonList("9584640c-3804-4dcd-997b-93593cfb9ea7")
		);
		var tokenUrl =
			keycloak.getAuthServerUrl() +
			"/realms/workshop/protocol/openid-connect/token";
		JsonNode jsonNode = restTemplate.postForObject(
			tokenUrl,
			new HttpEntity<>(map, httpHeaders),
			JsonNode.class
		);

		assert jsonNode != null;
		return jsonNode.get("access_token").asText();
	}

	protected LinkedMultiValueMap<String, String> getRestTemplateHeaders() {
		var headers = new LinkedMultiValueMap<String, String>();
		headers.add(
			HttpHeaders.CONTENT_TYPE,
			MediaType.APPLICATION_JSON.toString()
		);

		var token = getToken();
		headers.add("Authorization", "Bearer " + token);
		return headers;
	}

	protected String getFileContents(String path) throws IOException {
		File file = ResourceUtils.getFile(this.getClass().getResource(path));
		String data = new String(Files.readAllBytes(file.toPath()));
		return data;
	}

	@BeforeEach
	public void beforeEach() {
		if (!firstTestDelayMet) {
			try {
				Thread.sleep(10000L);
				firstTestDelayMet = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@DynamicPropertySource
	public static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> dbContainer.getJdbcUrl());
		registry.add(
			"spring.datasource.driverClassName",
			() -> dbContainer.getDriverClassName()
		);
		registry.add(
			"spring.datasource.username",
			() -> dbContainer.getUsername()
		);
		registry.add(
			"spring.datasource.password",
			() -> dbContainer.getPassword()
		);

		registry.add(
			"spring.security.oauth2.resourceserver.jwt.issuer-uri",
			() -> keycloak.getAuthServerUrl() + "/realms/workshop"
		);
		registry.add(
			"spring.security.oauth2.resourceserver.jwt.jwk-set-uri",
			() ->
				keycloak.getAuthServerUrl() +
				"/realms/workshop/protocol/openid-connect/certs"
		);
	}
}
