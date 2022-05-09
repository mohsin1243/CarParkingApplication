package com.cg.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int billId;

	@Column(name = "date")
	private LocalDate date;

	@Column(name = "entryTime")
	private LocalTime entryTime;

	@Column(name = "exitTime")
	private LocalTime exitTime;

	@Column(name = "slotNo")
	private String slotNo;

	@Column(name = "amount")
	private double amount;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "customerId")
	private Customer customer;

	public Bill() {
		super();
	}

	public Bill(int billId, LocalDate date, LocalTime entryTime, LocalTime exitTime, String slotNo, double amount,
			Customer customer) {
		super();
		this.billId = billId;
		this.date = date;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
		this.slotNo = slotNo;
		this.amount = amount;
		this.customer = customer;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(LocalTime entryTime) {
		this.entryTime = entryTime;
	}

	public LocalTime getExitTime() {
		return exitTime;
	}

	public void setExitTime(LocalTime exitTime) {
		this.exitTime = exitTime;
	}

	public String getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(String slotNo) {
		this.slotNo = slotNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Bill [billId=" + billId + ", date=" + date + ", entryTime=" + entryTime + ", exitTime=" + exitTime
				+ ", slotNo=" + slotNo + ", amount=" + amount + ", customer=" + customer + "]";
	}

}
