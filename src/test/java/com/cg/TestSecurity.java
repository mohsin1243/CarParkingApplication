package com.cg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.entity.Bill;
import com.cg.entity.Token;
import com.cg.service.SecurityService;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestSecurity {

	//Check for Correct token Count
	@Test
	void testGetTotalTokenCount() {
		Token.setTokenCount(100);
		assertEquals(100, Token.getTokenCount());
	}

	//Check token is issued if available
	@Test
	void testIssueToken() {
		Token.setTokenCount(10);
		SecurityService securityservice = new SecurityService();
		assertEquals(true, securityservice.issueToken());
		Token.setTokenCount(0);
		assertEquals(false, securityservice.issueToken());
	}

	//Verify Customer has parkedAt correct slot or not
	@Test
	void testVerifySlot() {
		Bill receipt = new Bill();
		receipt.setSlotNo("A11");
		boolean ans;
		ans = receipt.getSlotNo().equalsIgnoreCase("A11") ? true : false;
		assertEquals(true, ans);
		ans = receipt.getSlotNo().equalsIgnoreCase("A10") ? true : false;
		assertEquals(false, ans);
	}

	//check set count
	@Test
	void testSetToken() {
		Token.setTokenCount(101);
		assertEquals(101, Token.getTokenCount());
	}

	@Test
	void testGetTokenCount() {
		Token.setTokenCount(102);
		assertEquals(102, Token.getTokenCount());
	}

	@Test
	void testSetTokenCount() {
		Token.setTokenCount(103);
		assertEquals(103, Token.getTokenCount());
	}

}