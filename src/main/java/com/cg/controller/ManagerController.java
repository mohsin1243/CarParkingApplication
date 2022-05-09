package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Slot;
import com.cg.service.ManagerService;

@RestController
@RequestMapping(path = "/api/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	@GetMapping("/allSlot")
	public List<Slot> getAllPosition() {
		return managerService.getAllParkingSlots();
	}

	@GetMapping("/availableSlot")
	public List<Slot> getAvailablePosition() {
		return managerService.showAvailableParkingSlots();
	}

	@GetMapping("/nearestSlot")
	public List<Slot> getNearestAvailablePosition() {
		return managerService.showNearestTenParkingSlots();
	}

	@GetMapping("/genReceipt/{customerId}")
	public String getReceipt(@PathVariable("customerId") int customerId) {
		return managerService.generateReceipt(customerId);
	}

	@GetMapping("/genBill/{receiptId}")
	public String getBill(@PathVariable("receiptId") int receiptId) {
		return managerService.generateBill(receiptId);
	}

}
