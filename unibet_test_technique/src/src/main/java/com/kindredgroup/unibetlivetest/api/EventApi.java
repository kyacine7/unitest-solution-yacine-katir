package com.kindredgroup.unibetlivetest.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kindredgroup.unibetlivetest.service.EventService;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping(Urls.BASE_PATH)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventApi {
	
	private final EventService eventService;

	/**
	 * TODO @GetMapping(Urls.EVENTS)
	 */
	@GetMapping(Urls.EVENTS)
	public ResponseEntity getEvents(@RequestParam(value = "isLive", required = false) Boolean isLive) {
		  
		Optional eventsOptional = this.eventService.getEventsAlive(isLive);
		
		if(eventsOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(eventsOptional.get());			
		}
		
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	/**
	 * TODO @GetMapping(Urls.SELECTIONS)
	 */
	@GetMapping(Urls.SELECTIONS)
	public ResponseEntity getSelections(@PathVariable(value = "id") long id, 
			@RequestParam(value = "status", required = false) SelectionState state ) {
		
		Optional selectionsOptional = this.eventService.getEventSelection(id, state);
		if (selectionsOptional.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(selectionsOptional.get());			
		}

		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
	
}
