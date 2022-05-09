package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	public Bill findByCustomerCustomerId(int id);
	public boolean existsByCustomerCustomerId(int id);

}
