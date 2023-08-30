package com.inditex.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.inditex.dto.PriceDTO;
import com.inditex.model.Price;
import com.inditex.repository.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {
    // Inyección del repositorio PriceRepository para acceder a la base de datos
    @Autowired
    private PriceRepository repository;

    /**
     * Método para encontrar el precio aplicable para un producto de una marca en una fecha y hora específicas.
     *
     * @param brandId   - El identificador de la marca
     * @param productId - El identificador del producto
     * @param dateTime  - La fecha y hora específicas
     * @return PriceDTO - Un objeto DTO que contiene información sobre el precio aplicable
     */
    @CircuitBreaker(name = "priceService", fallbackMethod = "fallbackForFindApplicablePrices")
    @Override
    public PriceDTO findApplicablePrice(Integer brandId, Integer productId, LocalDateTime dateTime) {
        // Obtener todos los precios aplicables para los parámetros dados
        List<Price> prices = repository.findApplicablePrices(brandId, productId, dateTime);
        
        // Ordenar la lista de precios por prioridad de manera descendente y seleccionar el primero
        Price highestPriorityPrice = prices.stream()
                                           .sorted(Comparator.comparing(Price::getPriority).reversed())
                                           .findFirst()
                                           .orElse(null);
        
        // Devolver el PriceDTO si se encuentra un precio, de lo contrario, devolver null
        return highestPriorityPrice != null ? new PriceDTO(highestPriorityPrice) : null;
    }

    /**
     * Método de fallback para el Circuit Breaker.
     * Se invoca cuando el método findApplicablePrice falla.
     *
     * @param brandId   - El identificador de la marca
     * @param productId - El identificador del producto
     * @param dateTime  - La fecha y hora específicas
     * @param t         - La excepción que provocó el fallo
     * @return null     - En este caso, simplemente devolvemos null
     */
    public PriceDTO fallbackForFindApplicablePrices(Integer brandId, Integer productId, LocalDateTime dateTime, Throwable t) {
        return null;
    }
}





