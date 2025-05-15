package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {


	@Query(value = "SELECT obj "
			+ "FROM Sale obj JOIN FETCH obj.seller seller WHERE UPPER(seller.name) "
			+ "LIKE UPPER(CONCAT('%', :name, '%')) AND obj.date BETWEEN :minDate AND :maxDate", 
			countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller seller WHERE UPPER(seller.name) "
					+ "LIKE UPPER(CONCAT('%', :name, '%')) AND obj.date BETWEEN :minDate AND :maxDate")
	public Page<Sale> searchAllSales(LocalDate minDate, LocalDate maxDate, String name, Pageable page);
	
	@Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(obj.seller.name, SUM(obj.amount)) "
			+ "FROM Sale obj JOIN obj.seller seller WHERE obj.date BETWEEN :minDate AND :maxDate GROUP BY obj.seller.name")
	public List<SaleSummaryDTO> summary(LocalDate minDate, LocalDate maxDate);
}
