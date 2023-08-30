package com.inditex.service;

import java.time.LocalDateTime;

import com.inditex.dto.PriceDTO;


public interface PriceService {

	public PriceDTO findApplicablePrice(Integer brandId, Integer productId, LocalDateTime dateTime);
}
