package com.kindredgroup.unibetlivetest.dto;

import java.math.BigDecimal;

import com.kindredgroup.unibetlivetest.types.SelectionResult;
import com.kindredgroup.unibetlivetest.types.SelectionState;

import lombok.Data;

@Data
public class SelectionDto {

    private Long id;
    private String name;
    BigDecimal currentOdd;
    SelectionState state;
    SelectionResult result;
}
