package sh.demo.employee.events;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.FunctionProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.cloud.function.context.config.RoutingFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import sh.demo.employee.events.EmployeesUpdateAccepted;
import sh.demo.employee.services.EmployeesService;

@Component
@Slf4j
public class EventConsumer {

	@Autowired
	EmployeesService employeesService;

	@Bean
	public Consumer<EmployeesUpdateAccepted> employeesUpdateAccepted() {
		return event -> {
			try {
				log.info(
					"Consumed event of employees {} ",
					event.getData().getEmployeesId()
				);
				employeesService.handleEmployeesUpdateAccepted(
					event.getData().getContext(),
					event.getData().getEmployeesId(),
					event.getData().getUpdateEmployeesInput()
				);
				log.info(
					"Processing complete for event employees {}",
					event.getData().getEmployeesId()
				);
			} catch (Exception e) {
				log.error(
					"Error while processing for Employees {} ",
					event.getData().getEmployeesId(),
					e
				);
				throw e;
			}
		};
	}

	@Bean(value = "customRouter")
	public RoutingFunction customRouter(
		FunctionCatalog functionCatalog,
		@Nullable MessageRoutingCallback routingCallback,
		BeanFactory beanFactory,
		FunctionProperties functionProperties
	) {
		String EVENT_TYPE = "eventType";

		Map<String, String> evenTypeToFunctionNameMap = new HashMap<>();
		evenTypeToFunctionNameMap.put(
			"sh.demo.employee.events.EmployeesUpdateAccepted",
			"employeesUpdateAccepted"
		);

		var cb1 = new MessageRoutingCallback() {
			@Override
			public String routingResult(Message<?> message) {
				return evenTypeToFunctionNameMap.get(
					new String(
						(byte[]) message.getHeaders().get(EVENT_TYPE),
						StandardCharsets.UTF_8
					)
				);
			}
		};
		Map<String, String> propertiesMap = new HashMap<>();
		return new RoutingFunction(
			functionCatalog,
			propertiesMap,
			new BeanFactoryResolver(beanFactory),
			cb1
		);
	}
}
