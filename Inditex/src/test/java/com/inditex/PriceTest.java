package com.inditex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inditex.dto.PriceDTO;
import com.inditex.service.PriceService;

@SpringBootTest
public class PriceTest {

    @Autowired
    private PriceService priceService;

    @Test
    public void test1() {
        testCommon(1, 35455, LocalDateTime.parse("2023-08-14T10:00:00"), new BigDecimal("24.50"), 10);
    }

    @Test
    public void test2() {
        testCommon(1, 35455, LocalDateTime.parse("2023-08-14T16:00:00"), new BigDecimal("25.45"), 11);
    }

    @Test
    public void test3() {
        testCommon(1, 35455, LocalDateTime.parse("2023-08-14T21:00:00"), new BigDecimal("26.45"), 12);
    }

    @Test
    public void test4() {
        testCommon(1, 35455, LocalDateTime.parse("2023-08-15T10:00:00"), new BigDecimal("27.50"), 13);
    }

    @Test
    public void test5() {
        testCommon(1, 35455, LocalDateTime.parse("2023-08-16T21:00:00"), new BigDecimal("28.50"), 14);
    }

    private void testCommon(Integer brandId, Integer productId, LocalDateTime dateTime, BigDecimal expectedPrice, Integer expectedPriceList) {
        PriceDTO result = priceService.findApplicablePrice(brandId, productId, dateTime);

        // Usar afirmaciones para validar el resultado
        assertNotNull(result, "El resultado no debe ser nulo");

        assertEquals(brandId, result.getBrandId(), "El ID de la marca debe coincidir");
        assertEquals(productId, result.getProductId(), "El ID del producto debe coincidir");
        assertEquals(expectedPriceList, result.getPriceList(), "La lista de precios debe coincidir");
        assertEquals(expectedPrice, result.getFinalPrice(), "El precio final debe coincidir");
    }
}

