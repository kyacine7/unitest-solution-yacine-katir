package com.kindredgroup.unibetlivetest.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kindredgroup.unibetlivetest.dto.SelectionDto;
import com.kindredgroup.unibetlivetest.entity.Event;
import com.kindredgroup.unibetlivetest.entity.Selection;
import com.kindredgroup.unibetlivetest.repository.EventRepository;
import com.kindredgroup.unibetlivetest.repository.SelectionRepository;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository; 

	private final SelectionRepository selectionRepository; 

	public Optional<List<Event>> getEventsAlive(Boolean isLive){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -5);

		if (isLive == Boolean.TRUE) {
			return Optional
					.of(this.eventRepository.findAll()
							.stream()
							.filter(event -> event.getStartDate().after(calendar.getTime()))
							.collect(Collectors.toList()));
		} else {
			return Optional
					.of(this.eventRepository.findAll());
		}		

	}

	public Optional<List<SelectionDto>> getEventSelection(Long id, SelectionState state){

		List<Selection> selections = this.selectionRepository.findAll();

		if(state != null) {
			selections = this.selectionRepository.getSelectionByStateEquals(state);
		}

		List<SelectionDto> selectionDtos = selections.stream()
				.filter(selection -> this.eventRepository
						.findById(id).get()
						.getMarkets()
						.contains(selection.getMarket()))
				.map(selection -> this.toSelectionDto(selection))
				.collect(Collectors.toList());

		return Optional.of(selectionDtos);

	}
	
	
	private SelectionDto toSelectionDto(Selection selection) {
		SelectionDto selectionDto = new SelectionDto();
		
		selectionDto.setId(selection.getId());
		selectionDto.setName(selection.getName());
		selectionDto.setCurrentOdd(selection.getCurrentOdd());
		selectionDto.setState(selection.getState());
		selectionDto.setResult(selection.getResult());
		
		return selectionDto;
	}
}
