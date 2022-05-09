package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Security;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Integer> {

}