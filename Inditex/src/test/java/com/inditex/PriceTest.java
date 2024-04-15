package com.inditex;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.inditex.service.PriceService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.inditex.dto.PriceDTO;
import com.inditex.controller.PriceController;

import java.time.LocalDateTime;

@WebMvcTest(PriceController.class)
public class PriceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    /**
     * Test para verificar que la respuesta del endpoint es correcta cuando los datos solicitados son válidos.
     * Este test valida el comportamiento del controlador cuando se le pide un precio específico en una fecha y hora determinadas.
     */
    @Test
    public void test1() throws Exception {
        // Preparar datos para el test
        PriceDTO expectedPrice = new PriceDTO();
        expectedPrice.setProductId(35455L);
        expectedPrice.setBrandId(1L);
        expectedPrice.setPriceList(1L);
        expectedPrice.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        expectedPrice.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        expectedPrice.setPrice(35.5);

        // Configurar el servicio para devolver los datos preparados cuando se invoquen con los parámetros específicos
        Mockito.when(priceService.getPrice(1, 35455, LocalDateTime.parse("2020-06-14T10:00:00")))
               .thenReturn(expectedPrice);

        // Ejecutar el test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices")
                .param("date", "2020-06-14T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                      "productId": 35455,
                      "brandId": 1,
                      "priceList": 1,
                      "startDate": "2020-06-14T00:00:00",
                      "endDate": "2020-12-31T23:59:59",
                      "price": 35.5
                    }
                    """));
    }
    
    /**
     * Test para verificar que la respuesta del endpoint es correcta cuando se consulta el precio en un rango horario específico.
     * Este test asegura que el controlador maneja correctamente las solicitudes durante períodos donde los precios pueden variar.
     */
    @Test
    public void test2() throws Exception {
        // Preparar datos para el test
        PriceDTO expectedPrice = new PriceDTO();
        expectedPrice.setProductId(35455L);
        expectedPrice.setBrandId(1L);
        expectedPrice.setPriceList(2L);
        expectedPrice.setStartDate(LocalDateTime.parse("2020-06-14T15:00:00"));
        expectedPrice.setEndDate(LocalDateTime.parse("2020-06-14T18:30:00"));
        expectedPrice.setPrice(25.45);

        // Configurar el servicio para devolver los datos preparados cuando se invoquen con los parámetros específicos
        Mockito.when(priceService.getPrice(1, 35455, LocalDateTime.parse("2020-06-14T16:00:00")))
               .thenReturn(expectedPrice);

        // Ejecutar el test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices")
                .param("date", "2020-06-14T16:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                      "productId": 35455,
                      "brandId": 1,
                      "priceList": 2,
                      "startDate": "2020-06-14T15:00:00",
                      "endDate": "2020-06-14T18:30:00",
                      "price": 25.45
                    }
                    """));
    }

    
    /**
     * Test para verificar la respuesta correcta del endpoint fuera de horarios promocionales.
     * Asegura que los precios regulares se manejan correctamente después del horario de cualquier promoción.
     */
    @Test
    public void test3() throws Exception {
        // Preparar datos para el test
        PriceDTO expectedPrice = new PriceDTO();
        expectedPrice.setProductId(35455L);
        expectedPrice.setBrandId(1L);
        expectedPrice.setPriceList(1L);
        expectedPrice.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        expectedPrice.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        expectedPrice.setPrice(35.5);

        // Configurar el servicio para devolver los datos preparados cuando se invoquen con los parámetros específicos
        Mockito.when(priceService.getPrice(1, 35455, LocalDateTime.parse("2020-06-14T21:00:00")))
               .thenReturn(expectedPrice);

        // Ejecutar el test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices")
                .param("date", "2020-06-14T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                      "productId": 35455,
                      "brandId": 1,
                      "priceList": 1,
                      "startDate": "2020-06-14T00:00:00",
                      "endDate": "2020-12-31T23:59:59",
                      "price": 35.5
                    }
                    """));
    }

    
    /**
     * Test para verificar la correcta aplicación de precios promocionales en la mañana.
     * Asegura que las promociones temporales se reflejan adecuadamente en las respuestas del API.
     */
    @Test
    public void test4() throws Exception {
        // Preparar datos para el test
        PriceDTO expectedPrice = new PriceDTO();
        expectedPrice.setProductId(35455L);
        expectedPrice.setBrandId(1L);
        expectedPrice.setPriceList(3L);
        expectedPrice.setStartDate(LocalDateTime.parse("2020-06-15T00:00:00"));
        expectedPrice.setEndDate(LocalDateTime.parse("2020-06-15T11:00:00"));
        expectedPrice.setPrice(30.5);

        // Configurar el servicio para devolver los datos preparados cuando se invoquen con los parámetros específicos
        Mockito.when(priceService.getPrice(1, 35455, LocalDateTime.parse("2020-06-15T10:00:00")))
               .thenReturn(expectedPrice);

        // Ejecutar el test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices")
                .param("date", "2020-06-15T10:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                      "productId": 35455,
                      "brandId": 1,
                      "priceList": 3,
                      "startDate": "2020-06-15T00:00:00",
                      "endDate": "2020-06-15T11:00:00",
                      "price": 30.5
                    }
                    """));
    }
    
    /**
     * Test para verificar la correcta aplicación de precios durante un periodo extenso.
     * Asegura que las tarifas establecidas para largos periodos sean aplicadas correctamente.
     */
    @Test
    public void test5() throws Exception {
        // Preparar datos para el test
        PriceDTO expectedPrice = new PriceDTO();
        expectedPrice.setProductId(35455L);
        expectedPrice.setBrandId(1L);
        expectedPrice.setPriceList(4L);
        expectedPrice.setStartDate(LocalDateTime.parse("2020-06-15T16:00:00"));
        expectedPrice.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        expectedPrice.setPrice(38.95);

        // Configurar el servicio para devolver los datos preparados cuando se invoquen con los parámetros específicos
        Mockito.when(priceService.getPrice(1, 35455, LocalDateTime.parse("2020-06-16T21:00:00")))
               .thenReturn(expectedPrice);

        // Ejecutar el test
        mockMvc.perform(MockMvcRequestBuilders.get("/api/prices")
                .param("date", "2020-06-16T21:00:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                      "productId": 35455,
                      "brandId": 1,
                      "priceList": 4,
                      "startDate": "2020-06-15T16:00:00",
                      "endDate": "2020-12-31T23:59:59",
                      "price": 38.95
                    }
                    """));
    }
}