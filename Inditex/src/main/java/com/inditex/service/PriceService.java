package com.inditex.service;

import java.time.LocalDateTime;

import com.inditex.dto.PriceDTO;


/**
 * Interfaz de servicio para definir operaciones relacionadas con la recuperación de precios para productos.
 * Esta interfaz define los métodos de alto nivel para interactuar con la información de precios en la aplicación.
 *
 * <p>Esta interfaz de servicio permite abstraer la lógica de negocio relacionada con los precios
 * de la implementación concreta de acceso a datos, promoviendo así una arquitectura limpia y separación de responsabilidades.</p>
 *
 * @author Luis Calderon
 */
public interface PriceService {

    /**
     * Obtiene el precio más aplicable para un producto específico de una marca en una fecha y hora dadas.
     * Este método es responsable de invocar la lógica de persistencia para encontrar el precio adecuado
     * basado en la prioridad y el rango de fechas activo para el producto especificado y la marca.
     *
     * @param brandId El identificador de la marca del producto.
     * @param productId El identificador del producto.
     * @param dateTime La fecha y hora para la cual se requiere el precio.
     * @return Un {@link PriceDTO} conteniendo los detalles del precio aplicable.
     * @throws PriceNotFoundException si no se encuentra un precio aplicable para los parámetros dados.
     */
    PriceDTO getPrice(Integer brandId, Integer productId, LocalDateTime dateTime);
}
