package com.cg.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	@Column(name = "name")
	private String name;
	@Column(name = "vehicleNumber", unique = true)
	private String vehicleNumber;
	@Pattern(regexp = "[1-9][0-9]{9}")
	@Column(name = "phoneNumber")
	private String phoneNumber;
	@Column(name = "hasToken")
	private boolean hasToken;
	@Column(name = "slotNo")
	private String slotNo;
	@OneToOne(mappedBy = "customer")
	private Bill bill;

	public Customer() {
		super();
	}

	public Customer(int customerId, String name, String vehicleNumber, String phoneNumber, boolean hasToken,
			String slotNo) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.vehicleNumber = vehicleNumber;
		this.phoneNumber = phoneNumber;
		this.hasToken = hasToken;
		this.slotNo = slotNo;
	}

	public Customer(int customerId, String name, String vehicleNumber, String phoneNumber, boolean hasToken,
			String slotNo, Bill bill) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.vehicleNumber = vehicleNumber;
		this.phoneNumber = phoneNumber;
		this.hasToken = hasToken;
		this.slotNo = slotNo;
		this.bill = bill;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isHasToken() {
		return hasToken;
	}

	public void setHasToken(boolean hasToken) {
		this.hasToken = hasToken;
	}

	public String getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(String slotNo) {
		this.slotNo = slotNo;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", vehicleNumber=" + vehicleNumber
				+ ", phoneNumber=" + phoneNumber + ", hasToken=" + hasToken + ", slotNo=" + slotNo + ", bill=" + bill
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(bill, customerId, hasToken, name, phoneNumber, slotNo, vehicleNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(bill, other.bill) && customerId == other.customerId && hasToken == other.hasToken
				&& Objects.equals(name, other.name) && Objects.equals(phoneNumber, other.phoneNumber)
				&& Objects.equals(slotNo, other.slotNo) && Objects.equals(vehicleNumber, other.vehicleNumber);
	}

}