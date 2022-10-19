package com.kindredgroup.unibetlivetest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Selection;

public interface BetRepository extends JpaRepository<Bet, Integer>{

	public Bet findBySelectionAndCustomer(Selection selection, Customer customer);
	
}
