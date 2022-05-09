package com.cg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	protected int id;
	@Column(name = "email")
	protected String email;

	@NotBlank
	@Column(name = "password")
	protected String password;

	@NotBlank
	@Column(name = "role")
	protected String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person() {
	}

	public Person(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Person(int id, String email, String password, String role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + "]";
	}

}
