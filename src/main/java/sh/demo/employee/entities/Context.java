package sh.demo.employee.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sh.demo.employee.entities.UserContext;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Context {

	private UserContext userContext;

	private boolean isAuthenticated;
}
