package com.cg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
