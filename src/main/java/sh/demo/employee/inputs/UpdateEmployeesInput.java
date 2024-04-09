package sh.demo.employee.inputs;

import jakarta.persistence.CascadeType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sh.demo.employee.models.Address;

@NoArgsConstructor
@Getter
@Setter
public class UpdateEmployeesInput {

	private String employeeid;

	private String firstname;

	private String lastname;

	private String email;

	private String department;

	private String position;

	private String manager;

	private String hiredate;

	private Integer salary;

	private Address address;
}
