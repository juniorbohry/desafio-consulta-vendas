package com.devsuperior.dsmeta.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller " +
            "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
            "AND obj.date >= :minDate AND obj.date <= :maxDate",
            countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller " +
                        "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " +
                    "AND obj.date >= :minDate AND obj.date <= :maxDate"
            )
    Page<Sale> searchAll(String name, LocalDate minDate, LocalDate maxDate, Pageable pageable);


}
