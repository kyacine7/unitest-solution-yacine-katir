package com.kindredgroup.unibetlivetest.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BetDto {

    private Long selectionId;
    
    private BigDecimal cote;
    
    private BigDecimal mise;

}
