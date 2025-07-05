package br.com.sales.support.panel.ssp.domain.common;

import br.com.sales.support.panel.ssp.domain.exceptions.DomainException;

import java.math.BigDecimal;

public record Money(BigDecimal value) {

    public Money {
        if (value == null) {
            value = BigDecimal.ZERO;
        }

        if (BigDecimal.ZERO.compareTo(value) > 0) {
            throw new DomainException("Money cannot be less than zero");
        }
    }

}
