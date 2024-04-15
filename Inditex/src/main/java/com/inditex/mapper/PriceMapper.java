package com.inditex.mapper;

import org.mapstruct.Mapper;

import com.inditex.dto.PriceDTO;
import com.inditex.model.Price;


/**
 * Mapper para convertir entidades de tipo {@link Price} a su correspondiente {@link PriceDTO}.
 * Utiliza MapStruct, una herramienta que genera implementaciones de mappers en tiempo de compilación
 * para un rendimiento óptimo, reduciendo la necesidad de código boilerplate manual para el mapeo.
 *
 * <p>Esta interfaz declara métodos que MapStruct utilizará para generar automáticamente la
 * implementación requerida, inyectando la implementación generada en el contexto de Spring
 * gracias a la anotación {@link org.mapstruct.Mapper} con el atributo 'componentModel' establecido en "spring".</p>
 *
 * <p>Esto facilita la integración con Spring y el manejo de dependencias dentro de los mappers.</p>
 *
 * @author Luis Calderon
 */
@Mapper(componentModel = "spring")
public interface PriceMapper {
    
    /**
     * Convierte una entidad {@link Price} a un {@link PriceDTO}.
     * Este método proporciona una manera automática de copiar datos entre la entidad de modelo
     * de negocio y el objeto de transferencia de datos que se utiliza en las capas más externas
     * de la aplicación, como los controladores REST.
     *
     * @param price La entidad de Price que será convertida.
     * @return Un objeto {@link PriceDTO} que contiene los datos de la entidad.
     */
    PriceDTO toPriceDTO(Price price);
}
