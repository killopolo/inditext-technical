package com.inditex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejador global de excepciones para la aplicación.
 * Esta clase centraliza la gestión de excepciones para la API, permitiendo una respuesta coherente
 * y adecuada frente a errores que puedan surgir durante el procesamiento de las peticiones.
 * Utiliza {@link ControllerAdvice} para interceptar excepciones lanzadas por métodos
 * de manejo de solicitudes, facilitando así una gestión de errores más limpia y modular.
 *
 * @author Luis Calderon
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones específicas de tipo {@link PriceNotFoundException}.
     * Este método se invoca automáticamente cuando se lanza una {@link PriceNotFoundException}
     * en cualquier parte de la aplicación controlada por Spring MVC.
     *
     * @param ex La excepción capturada de tipo {@link PriceNotFoundException}.
     * @return Una respuesta {@link ResponseEntity} que encapsula el mensaje de error y el código
     * de estado HTTP adecuado para el error, en este caso, 404 Not Found.
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<Object> handlePriceNotFoundException(PriceNotFoundException ex) {
        // El cuerpo de la excepción se usa para construir la parte del mensaje de la respuesta.
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}