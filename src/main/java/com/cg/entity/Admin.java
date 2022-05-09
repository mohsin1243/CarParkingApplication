package com.cg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

@Entity
public class Admin extends Person {

	@Column(name = "firstname")
	private String firstName;
	@Column(name = "lastname")
	private String lastName;
	@Pattern(regexp = "[1-9][0-9]{9}")
	@Column(name = "phonenumber")
	private String phoneNo;

	@Column(name = "address")
	private String address;

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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Admin() {
		super();
	}

	@Override
	public String toString() {
		return "Admin [firstName=" + firstName + ", lastName=" + lastName + ", phoneNo=" + phoneNo + ", address="
				+ address + "]";
	}

}
