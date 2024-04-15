package com.inditex.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Entidad que representa un precio específico de un producto dentro de una marca en un rango de fechas.
 * Esta entidad se mapea a una tabla en la base de datos que almacena los precios de los productos,
 * permitiendo un control detallado de la variabilidad de precios a lo largo del tiempo.
 *
 * <p>Utiliza {@link LocalDateTime} para las marcas de tiempo que aseguran precisión en términos de
 * fechas y horas específicas, facilitando la gestión de precios que pueden cambiar múltiples veces dentro de un mismo día.</p>
 *
 * @author Luis Calderon
 */
@Entity
@Data
@NoArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identificador de la marca asociada al precio del producto.
     */
    private Long brandId;

    /**
     * Fecha y hora de inicio de validez del precio del producto.
     */
    private LocalDateTime startDate;

    /**
     * Fecha y hora de fin de validez del precio del producto.
     */
    private LocalDateTime endDate;

    /**
     * Lista de precios a la que pertenece este precio específico.
     */
    private Long priceList;

    /**
     * Identificador del producto específico al que se aplica este precio.
     */
    private Long productId;

    /**
     * Prioridad del precio, utilizada para determinar qué precio aplicar en caso de superposiciones.
     */
    private Long priority;

    /**
     * El precio efectivo del producto en la moneda especificada por {@link #curr}.
     */
    private Double price;

    /**
     * Código de la moneda en la que se expresa el precio, por ejemplo, EUR para euros.
     */
    private String curr;
}
