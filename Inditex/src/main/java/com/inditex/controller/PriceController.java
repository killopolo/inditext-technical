package com.inditex.controller;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inditex.dto.PriceDTO;
import com.inditex.service.PriceService;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    
    // Inyección de dependencia del servicio PriceService
    @Autowired
    private PriceService service;
    
    /**
     * Endpoint para obtener el precio aplicable basado en la fecha, el producto y la marca.
     * 
     * @param date      - La fecha y hora para la que se quiere obtener el precio.
     * @param productId - El ID del producto para el que se quiere obtener el precio.
     * @param brandId   - El ID de la marca para la que se quiere obtener el precio.
     * @return ResponseEntity<PriceDTO> - El precio aplicable encapsulado en un ResponseEntity.
     */
    @GetMapping
    public ResponseEntity<PriceDTO> getPrices(
        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
        @RequestParam("productId") Integer productId,
        @RequestParam("brandId") Integer brandId) {
        
        // Llamada al servicio para encontrar el precio aplicable
        PriceDTO priceResponseDTO = service.findApplicablePrice(brandId, productId, date);
        
        // Verificación del resultado y preparación de la respuesta HTTP
        if (priceResponseDTO != null) {
            return new ResponseEntity<>(priceResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}


