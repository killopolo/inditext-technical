package com.inditex.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inditex.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    /**
     * Método para encontrar precios aplicables basados en los parámetros dados.
     * Utiliza una consulta JPQL para seleccionar los precios que coinciden con los criterios.
     *
     * @param brandId   - El identificador de la marca para la que se buscan los precios.
     * @param productId - El identificador del producto para el que se buscan los precios.
     * @param dateTime  - La fecha y hora específicas para la que se buscan los precios.
     * @return List<Price> - Una lista de objetos Price que cumplen con los criterios.
     */
    @Query("SELECT p FROM Price p WHERE p.brandId = :brandId AND p.productId = :productId AND p.startDate <= :dateTime AND p.endDate >= :dateTime")
    List<Price> findApplicablePrices(
        @Param("brandId") Integer brandId, 
        @Param("productId") Integer productId, 
        @Param("dateTime") LocalDateTime dateTime
    );
}



