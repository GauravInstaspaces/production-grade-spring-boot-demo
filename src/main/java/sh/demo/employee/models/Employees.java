package sh.demo.employee.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import java.lang.Override;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sh.demo.employee.models.Address;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employees {

	private String employeeid;

	private String firstname;

	private String lastname;

	private String email;

	private String department;

	private String position;

	private String manager;

	private String hiredate;

	private Integer salary;

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(
		name = "addressId",
		insertable = true,
		updatable = true,
		referencedColumnName = "addressId"
	)
	private Address address;

	@Id
	private String employeesId;

	@Override
	public String toString() {
		return employeesId;
	}

	public static String getNewEmployeesId() {
		return UUID.randomUUID().toString();
	}
}
