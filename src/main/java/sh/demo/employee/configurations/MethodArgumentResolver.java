package sh.demo.employee.configurations;

import java.lang.Exception;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sh.demo.employee.entities.Context;
import sh.demo.employee.entities.UserContext;

public class MethodArgumentResolver implements HandlerMethodArgumentResolver {

	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameter().getType().equals(Context.class);
	}

	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) throws Exception {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Context context = new Context();
		UserContext userContext = new UserContext(
			securityContext.getAuthentication().getName()
		);
		context.setUserContext(userContext);
		context.setAuthenticated(true);
		return context;
	}
}
