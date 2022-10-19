package com.kindredgroup.unibetlivetest.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kindredgroup.unibetlivetest.dto.BetDto;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.service.BetService;
import com.kindredgroup.unibetlivetest.service.CustomerService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(Urls.BASE_PATH)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BetApi {

	private final BetService betService;

	private final CustomerService customerService;

	/**
	 * TODO @PostMapping(Urls.ADD_BET)
	 */
	@PostMapping(Urls.ADD_BET)
	public ResponseEntity addBet(@RequestBody BetDto betDto) {

		Customer customer = this.customerService.findCustomerByPseudo("unibest");

		switch (this.betService.addBet(betDto, customer)) {
		case ALREADY_EXISTS:
			return throw;

		case CHANGED_ODD:
			return ResponseEntity.status(HttpStatus.C).body("");
		
		case CLOSED_SELECTION:
			return ResponseEntity.status(HttpStatus.OK).body("");

		case INSUFFICIENT_BALANCE:
			return ResponseEntity.status(HttpStatus.OK).body("");

		default:
			return ResponseEntity.status(HttpStatus.OK).body("");
		}

		
	}

}
