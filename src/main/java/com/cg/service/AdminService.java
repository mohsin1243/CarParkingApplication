package com.cg.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cg.entity.Manager;
import com.cg.entity.Security;
import com.cg.entity.Slot;
import com.cg.entity.Token;
import com.cg.repository.AdminRepository;
import com.cg.repository.ManagerRepository;
import com.cg.repository.SecurityRepository;
import com.cg.repository.SlotRepository;

@Service
public class AdminService {

	@Autowired
	public AdminRepository adminRepository;
	@Autowired
	private SecurityRepository securityRepository;
	@Autowired
	private ManagerRepository managerRepository;
	@Autowired
	private SlotRepository slotRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	private void initializer() {
		Token.setTokenCount(slotRepository.getTotalVacantSlots());
	}

	// Adding Security
	public String addSecurity(Security security) {
		security.setPassword(passwordEncoder.encode(security.getPassword()));
		securityRepository.save(security);
		return "Security Added Successfully!";
	}

	// Removing Security
	public String removeSecurity(int securityId) {
		securityRepository.deleteById(securityId);
		return "Security Deleted Successfully!";
	}

	// Adding Manager
	public String addManager(Manager manager) {
		manager.setPassword(passwordEncoder.encode(manager.getPassword()));
		managerRepository.save(manager);
		return "Manager Added Successfully!";
	}

	// Removing Manager
	public String removeManager(int managerId) {
		managerRepository.deleteById(managerId);
		return "Manager Deleted Successfully!";
	}

	// Adding Parking Slots
	public String addSlot(int totalSlot, String type, String mode) {

		Slot s = new Slot();

		// Generating Token as per Slots
		Token.setTokenCount(Token.getTokenCount() + totalSlot);
		
		// Creating Slots
		if (mode.equalsIgnoreCase("create")) {
			char var = 'A';
			String var1 = "A";
			int counter = 0;

			// Generating SlotNo and Adding Slot Table
			for (int i = 0; i < totalSlot; i++) {
				counter++;
				if (i % 20 == 0 && i != 0) {
					var = (char) (var + 1);
					var1 = "";
					var1 = Character.toString(var);
					counter = 1;

				}
				if (counter < 10) {
					s.setSlotNo(var1 + "0" + counter);
				} else {
					s.setSlotNo(var1 + counter);
				}
				s.setSlotStatus(type);
				slotRepository.save(s);
			}
			return totalSlot + " Slots Added Successfully";
		}
		// Extending Slots
		else if (mode.equalsIgnoreCase("insert")) {
			// Getting Last slotNo
			Slot lastSlotRepo = slotRepository.findTopByOrderBySlotNoDesc();
			String lastSlot = lastSlotRepo.getSlotNo();
			char l = lastSlot.charAt(0);
			String ls = lastSlot.substring(1);
			int lst = Integer.parseInt(ls);
			String val = l + "";
			int c = lst;

			// Generating SlotNo and Adding Slot Table
			for (int i = lst; i < totalSlot + lst; i++) {
				c++;
				if (i % 20 == 0) {
					l = (char) (l + 1);
					val = "";
					val = Character.toString(l);
					c = 1;
				}
				if (c < 10) {
					s.setSlotNo(val + "0" + c);
				} else {
					s.setSlotNo(val + c);
				}
				s.setSlotStatus(type);
				slotRepository.save(s);
			}
			return totalSlot + " Slots Inserted Successfully";
		}
		return "Please Choose Correct Status";
	}

	// Remove Slot
	public String removeSlot(int decr, String slotPos) {
		
		char l = slotPos.charAt(0);
		String ls = slotPos.substring(1);
		int lst = Integer.parseInt(ls);
		String val = l + "";
		int c = lst;
		for (int i = lst; i < decr + lst; i++) {
			if (i % 20 == 0) {
				l = (char) (l + 1);
				val = "";
				val = Character.toString(l);
				c = 1;
			}
			if (c < 10) {
				slotPos = val + "0" + c;
			} else {
				slotPos = val + c;
			}
			// Deleting Slot
			slotRepository.deleteBySlotNo(slotPos);
			c++;
		}

		// Updating Token Count
		Token.setTokenCount(slotRepository.getTotalVacantSlots());
				
		return decr + " Slots Removed Successfully";
	}

	// For Updating Type at any slot Position
	public String updateSlot(String slotPos, String status) {
		
		Slot s = new Slot();
		  s = slotRepository.findBySlotNo(slotPos);
		  if(s!=null){
		   // Setting Updated Details
		   s.setSlotNo(slotPos);
		   s.setSlotStatus(status);
		   slotRepository.save(s);
		   
		   // Updating Token Count
		   Token.setTokenCount(slotRepository.getTotalVacantSlots());
		   
		   return slotPos + " is Update Successfully to " + status;
		  }
		  else {
		   return "Slot has been deleted...Kindly Enter the correct slot";
		  }
	}

	// Updating Slot Type for a Given Range
	public String rangeChangeStatusSlot(String start, String end, String type) {

		
		Slot s = new Slot();
		// Extracting start string
		char ch1 = start.charAt(0); // A,B,C
		String str1 = start.substring(1); // 01,02,03
		int val1 = Integer.parseInt(str1);
		String val2 = ch1 + "";
		int count = val1;

		// Extracting end string
		String str2 = end.substring(1);
		int val3 = Integer.parseInt(str2);
		
		// Setting Updated Slot Status and save Slot
		for (int i = val1; i <= val3; i++) {

			if (i % 20 == 0) {
				ch1 = (char) (ch1 + 1);
				val2 = "";
				val2 = Character.toString(ch1);
				count = 1;
			}
			if (count < 10) {
				s.setSlotNo(val2 + "0" + count);
			} else {
				s.setSlotNo(val2 + count);
			}
			count++;
			s.setSlotStatus(type);
			slotRepository.save(s);
		}
		
		// Updating Token Count
		Token.setTokenCount(slotRepository.getTotalVacantSlots());
		
		return "Status updated for " + start + " to " + end + " as " + type;
	}
}
