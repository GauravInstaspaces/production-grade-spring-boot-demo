package sh.demo.employee;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.lang.Class;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.ResourceUtils;

@ExtendWith(value = { MockitoExtension.class, SnapshotExtension.class })
public class BaseTest {

	protected Expect expect;

	protected String getFileContents(String path) throws IOException {
		File file = ResourceUtils.getFile(
			this.getClass().getResource("/" + path)
		);
		String data = new String(Files.readAllBytes(file.toPath()));
		return data;
	}

	private ObjectMapper getMapper() {
		var mapper = new Jackson2ObjectMapperBuilder().build();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.findAndRegisterModules();
		return mapper;
	}

	protected <T> T getResource(String path, Class<T> target)
		throws IOException {
		String data = getFileContents(path);
		var mapper = getMapper();
		return mapper.readValue(data, target);
	}

	protected <T> List<T> getResourceForList(String path, Class<T> target)
		throws IOException {
		String data = getFileContents(path);
		var mapper = getMapper();
		return mapper.readValue(
			data,
			mapper
				.getTypeFactory()
				.constructCollectionType(ArrayList.class, target)
		);
	}
}
