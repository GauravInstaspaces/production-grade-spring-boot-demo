package sh.demo.employee.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.lang.Override;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

	private String street;

	private String city;

	private String state;

	private String postalcode;

	private String country;

	@Id
	private String addressId;

	@Override
	public String toString() {
		return addressId;
	}

	public static String getNewAddressId() {
		return UUID.randomUUID().toString();
	}
}
