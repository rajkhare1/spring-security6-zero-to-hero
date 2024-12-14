package com.rajkhare.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import com.rajkhare.model.Loans;

@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {
//	@PreAuthorize(value = "hasRole('USER')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(long customerId);

}