package br.com.sales.support.panel.ssp.domain.common;

import br.com.sales.support.panel.ssp.domain.exceptions.ValidationException;

import java.math.BigDecimal;

public record Money(BigDecimal value) {

    public Money {
        if (value == null) {
            value = BigDecimal.ZERO;
        }

        if (BigDecimal.ZERO.compareTo(value) > 0) {
            throw new ValidationException("Money cannot be less than zero");
        }
    }

}
