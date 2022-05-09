package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Manager;
import com.cg.entity.Security;
import com.cg.service.AdminService;

@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/addManager")
	public String addManager(@RequestBody Manager manager) {
		return adminService.addManager(manager);
	}

	@DeleteMapping("/delManager/{managerId}")
	public String removeManager(@PathVariable("managerId") int managerId) {
		return adminService.removeManager(managerId);
	}

	@PostMapping("/addSecurity")
	public String addSecurity(@RequestBody Security security) {
		return adminService.addSecurity(security);
	}

	@DeleteMapping("/delSecurity/{securityId}")
	public String removeSecurity(@PathVariable("securityId") int securityId) {
		return adminService.removeSecurity(securityId);
	}

	@PostMapping("/addSlot/{mode}/{totalSlots}/{slotStatus}")
	public String addParking(@PathVariable("mode") String mode, @PathVariable("totalSlots") int totalSlots,
			@PathVariable("slotStatus") String slotStatus) {
		return adminService.addSlot(totalSlots, slotStatus, mode);
	}

	@DeleteMapping("/deleteSlot/{startFromSlotNo}/{totalDelSlotCount}")
	public String removeSlot(@PathVariable("totalDelSlotCount") int totalDelSlotCount,
			@PathVariable("startFromSlotNo") String startFromSlotNo) {
		return adminService.removeSlot(totalDelSlotCount, startFromSlotNo);
	}

	@PutMapping("/updateSlotStatus/{slotNo}/{updatedStatus}")
	public String update(@PathVariable("slotNo") String slotNo, @PathVariable("updatedStatus") String updatedStatus) {
		return adminService.updateSlot(slotNo, updatedStatus);
	}

	@PutMapping("/rangeUpdateSlotStatus/{startSlotNo}/{endSlotNo}/{updatedStatus}")
	public String rangechangeSlot(@PathVariable("startSlotNo") String startSlotNo,
			@PathVariable("endSlotNo") String endSlotNo, @PathVariable("updatedStatus") String updatedStatus) {
		return adminService.rangeChangeStatusSlot(startSlotNo, endSlotNo, updatedStatus);
	}
}
