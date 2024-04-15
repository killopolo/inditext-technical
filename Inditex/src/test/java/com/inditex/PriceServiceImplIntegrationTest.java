package com.inditex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import com.inditex.repository.PriceRepository;
import com.inditex.service.PriceService;
import com.inditex.service.PriceServiceImpl;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;
import com.inditex.mapper.PriceMapper;
import com.inditex.model.Price;
import com.inditex.dto.PriceDTO;
import org.mockito.quality.Strictness;



/**
 * Clase de pruebas de integración para {@link PriceServiceImpl}.
 * Utiliza Mockito para simular las dependencias y verificar que el servicio funciona correctamente
 * bajo condiciones controladas, asegurando que los métodos de la clase respondan como se espera.
 *
 * @ExtendWith(MockitoExtension.class) Permite la inicialización automática de objetos mock y
 *                                      inyección de dependencias dentro del contexto de pruebas con JUnit.
 * @MockitoSettings(strictness = Strictness.LENIENT) Permite que Mockito ignore los stubbings innecesarios,
 *                                                    evitando así errores en los tests donde no todos los mocks son utilizados.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PriceServiceImplIntegrationTest {

    @InjectMocks
    private PriceServiceImpl pricesServiceImpl;

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceMapper priceMapper;

    /**
     * Prueba el método getPrice para verificar que devuelve correctamente el DTO de precios
     * cuando los datos proporcionados coinciden con los existentes en la base de datos.
     */
    @Test
    void getPrice_returnPriceDTO_whenIsAllOk() {
        // given: Configuración del escenario de prueba
        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Price price = new Price();
        price.setId(1L);
        price.setBrandId(1L);
        price.setProductId(35455L);
        price.setPriceList(1L);
        price.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        price.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        price.setPrice(35.5);
        price.setPriority(0L);
        price.setCurr("EUR");

        PriceDTO expectedPriceDTO = new PriceDTO();
        expectedPriceDTO.setProductId(35455L);
        expectedPriceDTO.setBrandId(1L);
        expectedPriceDTO.setPriceList(1L);
        expectedPriceDTO.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        expectedPriceDTO.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        expectedPriceDTO.setPrice(35.5);

        // Stubbing the methods to return specific objects
        when(priceRepository.findTopPriorityPriceForCriteria(35455, 1, testDate))
                .thenReturn(Optional.of(price));
        when(priceMapper.toPriceDTO(price)).thenReturn(expectedPriceDTO);

        // when: Ejecución del método a probar
        PriceDTO actualPriceDTO = pricesServiceImpl.getPrice(1, 35455, testDate);

        // then: Verificaciones para asegurar que el resultado es el esperado
        assertEquals(expectedPriceDTO, actualPriceDTO);
    }
}