package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SellerSumaryDTO;
import com.devsuperior.dsmeta.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Autowired
	private SellerRepository sellerRepository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}


	public Page<SaleReportDTO> getReport(String name, String minDate, String maxDate, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate min = (minDate.isEmpty()) ? today.minusYears(1L) : LocalDate.parse(minDate);
		LocalDate max = (maxDate.isEmpty()) ? today : LocalDate.parse(maxDate);

		System.out.println(today);
		Page<Sale> result = repository.searchAll(name, min, max, pageable);
		return result.map(x -> new SaleReportDTO(x));
	}

	public Page<SellerSumaryDTO> getSumary(String minDate, String maxDate, Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate min = (minDate.isEmpty()) ? today.minusYears(1L) : LocalDate.parse(minDate);
		LocalDate max = (maxDate.isEmpty()) ? today : LocalDate.parse(maxDate);

		System.out.println(today);
		Page<SellerSumaryDTO> result = sellerRepository.searchSumary(min, max, pageable);

		return result;
	}



}
