package sh.demo.employee.events;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class BaseEvent<T> {

	private String eventId = UUID.randomUUID().toString();

	private Instant eventPublishedAt = OffsetDateTime.now().toInstant();

	private Instant eventCreatedAt = OffsetDateTime.now().toInstant();

	private String eventType;

	private T data;

	public BaseEvent(T data) {
		this.data = data;
	}
}
