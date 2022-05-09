package com.cg.service;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.Bill;
import com.cg.entity.Token;
import com.cg.repository.BillRepository;

@Service
public class SecurityService extends Token {

	private BillRepository billRepository;

	public SecurityService() {
	}

	@Autowired
	public SecurityService(BillRepository billRepository) {
		this.billRepository = billRepository;
	}

	// Get Total Available Token Count
	public String getTotalTokenCount() {
		return "All Available Token : " + Token.tokenCount;
	}

	// Issue Token to Customer
	public boolean issueToken() {
		if (Token.tokenCount > 0) {
			Token.setTokenCount(Token.getTokenCount() - 1);
			return true;
		} else {
			return false;
		}

	}

	// Verify Customers slotNo with Parked Position
	public boolean verifySlot(int receiptId, String slotParked) {
		// Get SlotNo from Receipt by receiptId
		try {
			Bill receipt = billRepository.findById(receiptId).get();
			// return true if correct position or else false
			if (receipt.getSlotNo().equalsIgnoreCase(slotParked)) {
				return true;
			}
		} catch (NullPointerException | NoSuchElementException e) {
			Logger log = LoggerFactory.getLogger(SecurityService.class);
			log.error("Null pointer exception thrown " + receiptId);
			return false;
		}
		return false;
	}

	// Optional Method to Set Token Count
	public int setToken(int tokenCount) {
		Token.setTokenCount(tokenCount);
		return Token.tokenCount;
	}

}
