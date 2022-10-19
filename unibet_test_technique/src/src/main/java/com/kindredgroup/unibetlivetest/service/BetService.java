package com.kindredgroup.unibetlivetest.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.kindredgroup.unibetlivetest.dto.BetDto;
import com.kindredgroup.unibetlivetest.entity.Bet;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.repository.BetRepository;
import com.kindredgroup.unibetlivetest.repository.SelectionRepository;
import com.kindredgroup.unibetlivetest.types.AddBetStatus;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class BetService {

	private final BetRepository betRepository;
	private final SelectionRepository selectionRepository;
		
	public AddBetStatus addBet(BetDto betDto, Customer customer) {
				
		Selection selection = this.selectionRepository.getById(betDto.getSelectionId());
		
		Bet bet = betRepository.findBySelectionAndCustomer(selection, customer);	
		
		if (bet != null) {
			return AddBetStatus.ALREADY_EXISTS;
		} 
		if (!selection.getCurrentOdd().equals(betDto.getCote())) {
			return AddBetStatus.CHANGED_ODD;
		} 
		if (selection.getState().equals(SelectionState.CLOSED)) {
			return AddBetStatus.CLOSED_SELECTION;
		} 
		if (betDto.getMise().compareTo(customer.getBalance()) > 0) {
			return AddBetStatus.INSUFFICIENT_BALANCE;
		}
		
		this.betRepository.save(this.mapToBet(betDto,customer,selection));
				
		return AddBetStatus.SUCCES;
	}

	private Bet mapToBet(BetDto betDto, Customer customer, Selection selection) {
		Bet bet = new Bet();
		
		bet.setCustomer(customer);
		bet.setSelection(selection);
		bet.setPlacement(betDto.getMise());
		bet.setDate(new Date());
		
		return bet;
	}
	
}
