package com.devsuperior.dsmeta.repositories;


import com.devsuperior.dsmeta.dto.SellerSumaryDTO;
import com.devsuperior.dsmeta.entities.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("""
    SELECT new com.devsuperior.dsmeta.dto.SellerSumaryDTO(
        s.name, 
        COALESCE(SUM(sa.amount), 0)
    )
    FROM Seller s 
    LEFT JOIN s.sales sa 
    WHERE sa.date BETWEEN :minDate AND :maxDate 
    GROUP BY s.id, s.name
    """)
    Page<SellerSumaryDTO> searchSumary(
            LocalDate minDate,
            LocalDate maxDate,
            Pageable pageable
    );



}
