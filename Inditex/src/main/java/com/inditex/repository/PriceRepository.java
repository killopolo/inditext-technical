package com.inditex.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inditex.model.Price;

/**
 * Repositorio JPA para manejar las operaciones de base de datos para la entidad {@link Price}.
 * Proporciona métodos personalizados además de los métodos CRUD típicos heredados de {@link JpaRepository}.
 *
 * <p>Este repositorio facilita la consulta de precios basada en criterios específicos como el identificador del producto,
 * el identificador de la marca y un rango de fechas, garantizando que se aplique el precio más relevante basado en la prioridad.</p>
 *
 * @author Luis Calderon
 */
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Recupera el precio más relevante para un producto dado, de una marca específica, aplicable en una fecha y hora determinadas.
     * Esta consulta considera la prioridad para resolver cualquier conflicto entre precios que se solapan en el rango de fechas.
     *
     * @param productId El identificador del producto para el cual se busca el precio.
     * @param brandId El identificador de la marca del producto.
     * @param applicationDate La fecha y hora exacta para la cual se necesita el precio.
     * @return Un {@link Optional} que contiene el {@link Price} si existe un precio aplicable, o vacío si no se encuentra ninguno.
     */
    @Query("SELECT p FROM Price p WHERE p.productId = :productId AND p.brandId = :brandId AND " +
           "p.startDate <= :applicationDate AND p.endDate >= :applicationDate " +
           "ORDER BY p.priority DESC")
    Optional<Price> findTopPriorityPriceForCriteria(
        @Param("productId") Integer productId, 
        @Param("brandId") Integer brandId, 
        @Param("applicationDate") LocalDateTime applicationDate);
}