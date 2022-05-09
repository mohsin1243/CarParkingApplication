package com.cg;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cg.entity.Bill;
import com.cg.entity.Customer;
import com.cg.entity.Slot;
import com.cg.entity.SlotStatus;
import com.cg.entity.Token;
import com.cg.repository.CustomerRepository;
import com.cg.repository.SlotRepository;
import com.cg.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestCustomer {

	@Autowired
	private CustomerService customerService;

	@MockBean
	private CustomerRepository customerRepository;

	@MockBean
	private SlotRepository slotRepository;

	//Check Add customer
	@Test
	void addCustomer() {
		Bill bill = new Bill();
		Customer customer = new Customer(1, "Kaumudi", "UP12AD1990", "8900110099", false, " ", bill);
		when(customerRepository.save(customer)).thenReturn(customer);
		assertEquals("Customer Added Successfully!", customerService.addCustomer(customer));
	}

	//Check for Token successfully issued by security
	@Test
	void getToken() {
		Bill bill = new Bill();
		Customer customer = new Customer(1, "Kaumudi", "UP12AD1990", "8900110099", false, " ", bill);
		Token.setTokenCount(1);
		customerRepository.save(customer);
		when(customerRepository.existsById(1)).thenReturn(true);
		when(customerRepository.findById(1))
				.thenReturn(Optional.of(new Customer(1, "Kaumudi", "UP12AD1990", "9876543210", false, " ", bill)));
		assertEquals("Token issued Successfully! to Customer ID : 1", customerService.getToken(1));

	}
	
	//Check for Token when token count is 0
	@Test
	void getToken2() {
		Bill bill = new Bill();
		Customer customer = new Customer(1, "Kaumudi", "UP12AD1990", "8900110099", false, " ", bill);
		Token.setTokenCount(0);
		customerRepository.save(customer);
		when(customerRepository.existsById(1)).thenReturn(true);
		when(customerRepository.findById(1))
				.thenReturn(Optional.of(new Customer(1, "Kaumudi", "UP12AD1990", "9876543210", false, " ", bill)));
		assertEquals("Token not Available, Parking is Full!", customerService.getToken(1));

	}

	//Checks for Vacant Slot is Alloted to customer successfully
	@Test
	void selectSlot() {
		Bill bill = new Bill();
		Customer customer = new Customer(1, "Kaumudi", "UP12AD1990", "8900110099", true, null, bill);
		customerRepository.save(customer);
		Slot slot = new Slot("A01", SlotStatus.VACANT);
		when(customerRepository.existsById(1)).thenReturn(true);
		when(slotRepository.save(slot)).thenReturn(slot);
		when(slotRepository.findBySlotNo("A01")).thenReturn(slot);
		when(customerRepository.findById(1))
				.thenReturn(Optional.of(new Customer(1, "Kaumudi", "UP12AD1990", "9876543210", true, null, bill)));
		assertEquals("Slot No : A01 is Alloted to Customer Id : 1", customerService.selectSlot(1, "A01"));

	}
	
	//Checks for Allocating slot to customer who do not have token
		@Test
		void selectSlot2() {
			Bill bill = new Bill();
			Customer customer = new Customer(1, "Kaumudi", "UP12AD1990", "8900110099", false, null, bill);
			customerRepository.save(customer);
			Slot slot = new Slot("A01", SlotStatus.VACANT);
			when(customerRepository.existsById(1)).thenReturn(true);
			when(slotRepository.save(slot)).thenReturn(slot);
			when(slotRepository.findBySlotNo("A01")).thenReturn(slot);
			when(customerRepository.findById(1))
					.thenReturn(Optional.of(new Customer(1, "Kaumudi", "UP12AD1990", "9876543210", false, null, bill)));
			assertEquals("Hey!, Kaumudi You need to First issue Token.", customerService.selectSlot(1, "A01"));

		}

}
