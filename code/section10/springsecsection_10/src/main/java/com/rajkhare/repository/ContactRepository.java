package com.rajkhare.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rajkhare.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
	
	
}