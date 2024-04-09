package sh.demo.employee.events;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import sh.demo.employee.events.BaseEvent;

@Component
public class EventPublisher {

	@Autowired
	StreamBridge streamBridge;

	private static final <T extends BaseEvent> Message<T> message(
		T val,
		String key
	) {
		return MessageBuilder
			.withPayload(val)
			.setHeader(KafkaHeaders.KEY, key)
			.setHeader(
				"eventType",
				val.getEventType().getBytes(StandardCharsets.UTF_8)
			)
			.build();
	}

	public void sendEmployeesEvent(BaseEvent event, String key) {
		streamBridge.send("customrouter-out-0", message(event, key));
	}
}
