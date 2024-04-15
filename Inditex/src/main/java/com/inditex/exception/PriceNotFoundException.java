package com.inditex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada para indicar que no se ha encontrado un precio aplicable.
 * Esta excepción se lanza cuando una consulta específica a la base de datos no retorna
 * ningún resultado para los criterios dados, lo cual significa que no existe un precio
 * configurado para los parámetros de producto, marca y fecha proporcionados.
 *
 * @author Luis Calderon
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND) // Esta anotación asegura que, cuando se lance esta excepción, se devuelva automáticamente un HttpStatus.NOT_FOUND (404) al cliente.
public class PriceNotFoundException extends RuntimeException {

    /**
     * Constructor que crea una nueva excepción con un mensaje específico.
     * Este mensaje describe la razón específica del error y será útil para los logs y para
     * devolver una respuesta más informativa al cliente.
     *
     * @param message El mensaje que detalla el motivo por el cual se lanza esta excepción.
     */
    public PriceNotFoundException(String message) {
        super(message);
    }
}
