package com.inditex.mapper;

import com.inditex.dto.PriceDTO;
import com.inditex.model.Price;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-15T21:56:13+0200",
    comments = "version: 1.5.2.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class PriceMapperImpl implements PriceMapper {

    @Override
    public PriceDTO toPriceDTO(Price price) {
        if ( price == null ) {
            return null;
        }

        PriceDTO priceDTO = new PriceDTO();

        priceDTO.setBrandId( price.getBrandId() );
        priceDTO.setEndDate( price.getEndDate() );
        priceDTO.setPrice( price.getPrice() );
        priceDTO.setPriceList( price.getPriceList() );
        priceDTO.setProductId( price.getProductId() );
        priceDTO.setStartDate( price.getStartDate() );

        return priceDTO;
    }
}
