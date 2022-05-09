package com.cg.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {
	List<Person> findByEmail(String email);
}
