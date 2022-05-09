package com.cg;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.cg.entity.Manager;
import com.cg.entity.Person;
import com.cg.entity.Security;
import com.cg.entity.SecurityType;
import com.cg.entity.Slot;
import com.cg.entity.SlotStatus;
import com.cg.repository.AdminRepository;
import com.cg.repository.ManagerRepository;
import com.cg.repository.SecurityRepository;
import com.cg.repository.SlotRepository;
import com.cg.service.AdminService;

@SpringBootTest
class TestAdmin {

	@MockBean
	private AdminRepository adminrepository;

	@MockBean
	private SecurityRepository securityrepository;

	@MockBean
	private ManagerRepository managerrepository;

	@Autowired
	private AdminService adminService;

	@MockBean
	private SlotRepository slotRepository;

	@Test
	void addSecurity() {
		Security security = new Security("Aditya", "Maurya", "9876543210", "Mumbai", SecurityType.ENTRY);
		security.setPassword("1234");
		when(securityrepository.save(security)).thenReturn(security);
		assertEquals("Security Added Successfully!", adminService.addSecurity(security));
	}

	@Test
	public void removeSecurity() {
		Person person = new Person(1, "security@carparking.com", "1234", "SECURITY");
		int id = person.getId();
		assertEquals("Security Deleted Successfully!", adminService.removeSecurity(id));

	}

	@Test
	void addManager() {
		Manager manager = new Manager("Mosin", "Sheikh", "Mumbai", "9877889900");
		manager.setPassword("1234");
		when(managerrepository.save(manager)).thenReturn(manager);
		assertEquals("Manager Added Successfully!", adminService.addManager(manager));
	}

	@Test
	public void removeManager() {
		Person person = new Person(1, "manager@carparking.com", "1234", "MANAGER");
		int id = person.getId();
		assertEquals("Manager Deleted Successfully!", adminService.removeManager(id));

	}

	@Test
	void addSlotTest() {
		Slot slot = new Slot("A01", SlotStatus.VACANT);
		when(slotRepository.save(slot)).thenReturn(slot);
		assertEquals("1 Slots Added Successfully", adminService.addSlot(1, "VACANT", "create"));
	}

	@Test
	void removeSlot() {
		adminService.removeSlot(1, "A02");
		assertEquals("1 Slots Removed Successfully", adminService.removeSlot(1, "A02"));

	}

	@Test
	void updateSlot() {
		Slot slot = new Slot("A02", SlotStatus.VACANT);
		when(slotRepository.save(slot)).thenReturn(slot);
		assertEquals("A02 is Update Successfully to RESERVE", adminService.updateSlot("A02", "RESERVE"));

	}

	@Test
	void rangeChangeStatusSlot() {
		Slot slot1 = new Slot("A01", SlotStatus.VACANT);
		Slot slot2 = new Slot("A02", SlotStatus.VACANT);
		Slot slot3 = new Slot("A03", SlotStatus.VACANT);

		Slot s = slotRepository.save(slot1);
		s = slotRepository.save(slot2);
		s = slotRepository.save(slot3);
		when(slotRepository.save(s)).thenReturn(s);
		assertEquals("Status updated for A01 to A03 as RESERVE",
				adminService.rangeChangeStatusSlot("A01", "A03", "RESERVE"));

	}

}
