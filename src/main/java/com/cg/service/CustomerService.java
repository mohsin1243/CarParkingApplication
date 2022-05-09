package com.cg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Customer;
import com.cg.entity.Slot;
import com.cg.repository.CustomerRepository;
import com.cg.repository.SlotRepository;

@Service
public class CustomerService {

	private CustomerRepository customerRepository;
	private SlotRepository slotRepository;

	private SecurityService securityService;

	@Autowired
	public CustomerService(CustomerRepository customerRepository, SlotRepository slotRepository,
			SecurityService securityService) {
		super();
		this.customerRepository = customerRepository;
		this.slotRepository = slotRepository;
		this.securityService = securityService;
	}

	public CustomerService() {
		super();
	}

	// Add Customer
	
	public String addCustomer(Customer customer){
		try{
		customerRepository.save(customer);
		}catch(Exception pe)
		{
			return "Customer Already Exists!";
		}
		return "Customer Added Successfully!";
	}
	
	// Get for Token from Primary Security
	public String getToken(int customerId) {
		if (customerRepository.existsById(customerId)) {
			// Get customer Details by customerId
			Customer customer = customerRepository.findById(customerId).get();

			if (customer.isHasToken() == false) {
				// Request for token to Primary Security
				if (securityService.issueToken()) {
					// Setting Token status and Save customer
					customer.setHasToken(true);
					customerRepository.save(customer);

					return "Token issued Successfully! to Customer ID : " + customer.getCustomerId();
				} else {
					return "Token not Available, Parking is Full!";
				}

			} else {
				return "Customer Id : " + customer.getCustomerId() + " has Already issued Token!";
			}
		} else {
			return "Entered Customer Id is wrong!";
		}

	}

	// Customer Selects Slot
	public String selectSlot(int customerId, String slotNo) {

		if (customerRepository.existsById(customerId)) {
			// Get Customer Details by Id
			Customer customer = customerRepository.findById(customerId).get();

			if (customer.isHasToken()) {
				if (slotRepository.findBySlotNo(slotNo) != null) {
					if (customer.getSlotNo() == null) {

						// Get Slot by SlotNo
						Slot s = slotRepository.findBySlotNo(slotNo);

						// Check Slot is 'Vacant' or Not
						if (s.getSlotStatus().toString().equalsIgnoreCase("VACANT")) {
							// Setting Slot
							customer.setSlotNo(slotNo);

							// Getting Slot and Changing Status to 'Vacant'
							Slot slot = slotRepository.findBySlotNo(slotNo);
							slot.setSlotStatus("OCCUPIED");

							// Save Customer
							customerRepository.save(customer);

							return "Slot No : " + slot.getSlotNo() + " is Alloted to " + "Customer Id : "
									+ customer.getCustomerId();

						} else {
							return "Can not Allocate Slot! Slot is " + s.getSlotStatus();
						}

					} else {
						return customer.getSlotNo() + " is Already Alloted to Customer Id : "
								+ customer.getCustomerId();
					}
				} else {
					return "Entered Slot is wrong or Not added by ADMIN!";
				}
			} else {
				return "Hey!, " + customer.getName() + " You need to First issue Token.";
			}
		} else {
			return "Customer Does not Exists!";
		}

	}

	// Verify Parked Location from Secondary Security
	public String parkAt(int receiptId, String parkedSlotNo) {
		if (securityService.verifySlot(receiptId, parkedSlotNo)) {
			return "Parked At Correct Slot";
		} else {
			return "Parked at Wrong Slot or Entered Bill Id is Wrong!";
		}
	}

}
