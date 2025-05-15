package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	public Page<SaleMinDTO> searchAllSales(String minDate, String maxDate, String name, Pageable pageRequest) {
		LocalDate initialDate;
		LocalDate finalDate;
		
		if(!maxDate.equals("")) {
			finalDate = LocalDate.parse(maxDate);
		} else {
			finalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		
		if(!minDate.equals("")) {
			initialDate = LocalDate.parse(minDate);
		} else {
			initialDate = finalDate.minusYears(1L);
		}
		
		Page<Sale> page = repository.searchAllSales(initialDate, finalDate, name, pageRequest);
		
		return page.map(x -> new SaleMinDTO(x));
	}
	
	public List<SaleSummaryDTO> summary(String minDate, String maxDate) {
		LocalDate initialDate;
		LocalDate finalDate;
		
		if(!maxDate.equals("")) {
			finalDate = LocalDate.parse(maxDate);
		} else {
			finalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		
		if(!minDate.equals("")) {
			initialDate = LocalDate.parse(minDate);
		} else {
			initialDate = finalDate.minusYears(1L);
		}
		
		List<SaleSummaryDTO> sale = repository.summary(initialDate, finalDate);
		
		return sale;
	}
}
