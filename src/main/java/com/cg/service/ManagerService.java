package com.cg.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Bill;
import com.cg.entity.Customer;
import com.cg.entity.Slot;
import com.cg.entity.Token;
import com.cg.repository.BillRepository;
import com.cg.repository.CustomerRepository;
import com.cg.repository.SlotRepository;

@Service
public class ManagerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private SlotRepository slotRepository;

	// Display All Added Parking Slots
	public List<Slot> getAllParkingSlots() {
		return slotRepository.findAll();
	}

	// Display All Vacant/Available Parking Slots
	public List<Slot> showAvailableParkingSlots() {
		return slotRepository.findAllAvailableSlot();
	}

	// Display Ten Nearest Vacant/Available Parking Slots
	public List<Slot> showNearestTenParkingSlots() {
		return slotRepository.findTop10ByOrderBySlotNoAsc();
	}

	// Add Customer in Bill Table and Generate Receipt
	public String generateReceipt(int customerId) {

		// Check whether customerId is available or not
		if (customerRepository.existsById(customerId)) {

			// Getting Customer Details by Id
			Customer customer = customerRepository.findById(customerId).get();

			if (customer.getSlotNo() != null) {

				// Creating new Bill Object
				Bill receipt = new Bill();


				boolean existsCustomerReceipt=billRepository.existsByCustomerCustomerId(customerId);
				
				if(!existsCustomerReceipt)
				{
						// Setting Receipt Details
						receipt.setCustomer(customer);
						receipt.setDate(LocalDate.now());
						receipt.setEntryTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
						receipt.setSlotNo(receipt.getCustomer().getSlotNo());
		
						// Saving Receipt Details in Bill Table
						billRepository.save(receipt);
		
						// Generating Receipt File
						try {
							FileWriter receiptFile = new FileWriter("Receipt.txt");
							receiptFile.append("\t\t==================================");
							receiptFile.append("\n");
							receiptFile.append("\t\t  TEAM 4 - CAR PARKING SYSTEM (Receipt)");
							receiptFile.append("\n");
							receiptFile.append("\t\t==================================");
							receiptFile.append("\n");
							receiptFile.append("----------------------------------");
							receiptFile.append("\n");
							receiptFile.append("Receipt Details");
							receiptFile.append("\n");
							receiptFile.append("----------------------------------");
							receiptFile.append("\n");
							receiptFile.append("Receipt Id : " + receipt.getBillId());
							receiptFile.append("\n");
							receiptFile.append("Date : " + receipt.getDate());
							receiptFile.append("\n");
							receiptFile.append("----------------------------------");
							receiptFile.append("\n");
							receiptFile.append("Customer Details");
							receiptFile.append("\n");
							receiptFile.append("----------------------------------");
							receiptFile.append("\n");
							receiptFile.append("Customer Id : " + customer.getCustomerId());
							receiptFile.append("\n");
							receiptFile.append("Name : " + customer.getName());
							receiptFile.append("\n");
							receiptFile.append("Phone Number : " + customer.getPhoneNumber());
							receiptFile.append("\n");
							receiptFile.append("Vehicle Number : " + customer.getVehicleNumber());
							receiptFile.append("\n");
							receiptFile.append("Parking Position : " + customer.getSlotNo());
							receiptFile.append("\n");
							receiptFile.append("Entry Time : " + receipt.getEntryTime());
							receiptFile.append("\n");
							receiptFile.close();
		
						} catch (IOException e) {
							e.printStackTrace();
						}
						return "Receipt Generated for Customer Id : " + customer.getCustomerId();
				} else {
						return "Receipt is already Generated!";
					}
			} else {
				return "Please! Choose Slot First to generate Receipt";
			}

		} else {
			return "Customer Id is wrong or Customer Does not Exists!";
		}
	}

	// Generate Final Bill with Amount
	public String generateBill(int receiptId) {

		try {
			// Check whether receiptId is available or not
			if (billRepository.findById(receiptId) != null) {

				// Getting Bill Details by billId
				Bill bill = billRepository.findById(receiptId).get();
				
				boolean existsCustomerBill=billRepository.existsByCustomerCustomerId(bill.getCustomer().getCustomerId());
				Bill getBilldata=billRepository.findByCustomerCustomerId(bill.getCustomer().getCustomerId());
				if(existsCustomerBill && getBilldata.getExitTime()==null)
				{
				
					// Setting Details
					bill.setExitTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
					double finalBillAmount = 60.0;
					long diffTotalDuration = ChronoUnit.HOURS.between(bill.getEntryTime(), bill.getExitTime());
	
					// Check for Duration : If 0 assign 1
					if (diffTotalDuration < 1) {
						diffTotalDuration = 1;
						bill.setAmount(finalBillAmount);
					} else {
						finalBillAmount = 60 * (double) diffTotalDuration;
						bill.setAmount(finalBillAmount);
					}
	
					// Saving Bill Details in Bill Table
					billRepository.save(bill);
	
					// Generating Final Bill in File
					try {
						FileWriter finalBill = new FileWriter("Bill.txt");
						finalBill.append("\t\t==================================");
						finalBill.append("\n");
						finalBill.append("\t\t     TEAM 4 CAR PARKING SYSTEM (Bill)");
						finalBill.append("\n");
						finalBill.append("\t\t==================================");
						finalBill.append("\n");
						finalBill.append("----------------------------------");
						finalBill.append("\n");
						finalBill.append("Bill Details");
						finalBill.append("\n");
						finalBill.append("----------------------------------");
						finalBill.append("\n");
						finalBill.append("Bill Id : " + bill.getBillId());
						finalBill.append("\n");
						finalBill.append("Date : " + bill.getDate());
						finalBill.append("\n");
						finalBill.append("----------------------------------");
						finalBill.append("\n");
						finalBill.append("Customer Details");
						finalBill.append("\n");
						finalBill.append("----------------------------------");
						finalBill.append("\n");
						finalBill.append("Customer Id : " + bill.getCustomer().getCustomerId());
						finalBill.append("\n");
						finalBill.append("Name : " + bill.getCustomer().getName());
						finalBill.append("\n");
						finalBill.append("Phone Number : " + bill.getCustomer().getPhoneNumber());
						finalBill.append("\n");
						finalBill.append("Vehicle Number : " + bill.getCustomer().getVehicleNumber());
						finalBill.append("\n");
						finalBill.append("Parking Position : " + bill.getCustomer().getSlotNo());
						finalBill.append("\n");
						finalBill.append("Entry Time : " + bill.getEntryTime());
						finalBill.append("\n");
						finalBill.append("Exit Time : " + bill.getExitTime());
						finalBill.append("\n");
						finalBill.append("Parking Duration (in Hours) : " + diffTotalDuration);
						finalBill.append("\n");
						finalBill.append("----------------------------------");
						finalBill.append("\n");
						finalBill.append("Payment (Charges : 60 Ruppes per Hour)");
						finalBill.append("\n");
						finalBill.append("----------------------------------");
						finalBill.append("\n");
						finalBill.append("Total Amount : " + bill.getAmount());
						finalBill.append("\n");
	
						finalBill.close();
	
						// Getting Customer details by Id from bill
						Customer customer = customerRepository.getById(bill.getCustomer().getCustomerId());
	
						// Calling Customer Exit to reset Values
						customerExit(customer);
						billRepository.deleteById(bill.getBillId());
					} catch (IOException e) {
						e.printStackTrace();
					}
					return "Bill generated for Customer Id : " + bill.getCustomer().getCustomerId();
				}else
				{
					return "Bill is Already generated!";
				}	
			} else {
				return "Bill can not be Generated";
			}
		} catch (NoSuchElementException ne) {
			return "Bill is Already generated! OR You have not Generated Receipt OR Entered Bill Id is Wrong!";
		}
	}

	// Customer Exit
	public void customerExit(Customer customer) {
		// Setting Customer hasToken to False and Increment token
		customer.setHasToken(false);

		Token.setTokenCount(Token.getTokenCount() + 1);
		// Getting Customer SlotNo
		String customersSlot = customer.getSlotNo();

		// Getting Slot using customerSlot and Setting Vacant
		Slot s = slotRepository.findBySlotNo(customersSlot);
		s.setSlotStatus("VACANT");

		// Setting Customer slotNo to Null
		customer.setSlotNo(null);

		// Saving Slot in Slot Table
		slotRepository.save(s);
	}

}
