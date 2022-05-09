package com.cg.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;

@Entity
public class Security extends Person {

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Pattern(regexp = "[1-9][0-9]{9}")
	@Column(name = "phone_number")
	private String phoneNumber;
	@Column(name = "address")
	private String address;
	@Column(name = "security_type")
	@Enumerated(EnumType.STRING)
	private SecurityType securityType;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public SecurityType getSecurityType() {
		return securityType;
	}

	public void setSecurityType(SecurityType securityType) {
		this.securityType = securityType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Security() {
	}

	public Security(String firstName, String lastName, String phoneNumber, String address, SecurityType securityType) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.securityType = securityType;
	}

	@Override
	public String toString() {
		return "Security [firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", address=" + address + ", securityType=" + securityType + "]";
	}
	
	

}
