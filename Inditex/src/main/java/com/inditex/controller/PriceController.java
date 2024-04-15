package com.inditex.controller;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.dto.PriceDTO;
import com.inditex.exception.PriceNotFoundException;
import com.inditex.service.PriceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RestController que maneja las peticiones relacionadas con los precios de los productos.
 * Este controlador ofrece un endpoint para recuperar el precio más adecuado de un producto en una fecha específica, 
 * basado en su id, el id de la marca y la fecha de aplicación del precio.
 *
 * Rutas:
 * - GET /api/prices: Recupera el precio aplicable para un producto específico de una marca en una fecha dada.
 */
@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private static final Logger log = LoggerFactory.getLogger(PriceController.class);
    
    @Autowired
    private PriceService service;

    /**
     * Obtiene el precio aplicable para un producto dado, una marca y una fecha.
     *
     * @param date La fecha y hora de aplicación del precio.
     * @param productId El ID del producto para el cual se busca el precio.
     * @param brandId El ID de la marca a la que pertenece el producto.
     * @return ResponseEntity que contiene PriceDTO si se encuentra un precio aplicable,
     *         o una ResponseEntity con un error HTTP 404 si no se encuentra ningún precio aplicable.
     */
    @GetMapping
    public ResponseEntity<PriceDTO> getPrices(
        @RequestParam("date") LocalDateTime date,
        @RequestParam("productId") Integer productId,
        @RequestParam("brandId") Integer brandId) {

        log.info("Fetching prices for date: {}, product ID: {}, brand ID: {}", date, productId, brandId);
        try {
            PriceDTO price = service.getPrice(brandId, productId, date);
            return ResponseEntity.ok(price);
        } catch (PriceNotFoundException e) {
            log.error("Price not found for date: {}, product ID: {}, brand ID: {}. Error: {}", date, productId, brandId, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}


