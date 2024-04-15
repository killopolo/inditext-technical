package com.inditex.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.inditex.dto.PriceDTO;
import com.inditex.exception.PriceNotFoundException;
import com.inditex.model.Price;
import com.inditex.repository.PriceRepository;

/**
 * Servicio que implementa la lógica de negocio para obtener precios de productos.
 * Esta clase es responsable de comunicarse con el repositorio de precios para obtener la información
 * necesaria y transformarla en un formato útil para los consumidores de este servicio.
 *
 * <p>La implementación asegura que se obtenga el precio más relevante y con la mayor prioridad
 * para un producto y marca en un momento dado, manejando también los casos en los que el precio no pueda ser encontrado.</p>
 *
 * @author Luis Calderon
 */
@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository repository;

    /**
     * Obtiene el precio más relevante para un producto específico de una marca en una fecha y hora dadas.
     * Este método consulta el repositorio de precios para encontrar el registro que mejor se ajuste
     * a los criterios especificados, basado en la prioridad y los rangos de fechas en que el precio es aplicable.
     *
     * @param brandId   El identificador de la marca del producto.
     * @param productId El identificador del producto.
     * @param dateTime  La fecha y hora específica para la cual se busca el precio.
     * @return PriceDTO Objeto que contiene la información del precio encontrado.
     * @throws PriceNotFoundException si no se encuentra un precio que cumpla con los criterios dados.
     */
    @Override
    public PriceDTO getPrice(Integer brandId, Integer productId, LocalDateTime dateTime) {
        return repository.findTopPriorityPriceForCriteria(productId, brandId, dateTime)
            .map(PriceDTO::new)
            .orElseThrow(() -> new PriceNotFoundException("No applicable price found for the given criteria."));
    }
}





