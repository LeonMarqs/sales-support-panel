package br.com.sales.support.panel.ssp.domain.common;

import br.com.sales.support.panel.ssp.domain.exceptions.ValidationException;

public record Name(String value) {

    public Name {
        if (value == null || value.isBlank()) {
            throw new ValidationException("Name cannot be empty");
        }

        value = value.toUpperCase();
    }

}
