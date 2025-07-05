package br.com.sales.support.panel.ssp.domain.exceptions;

public class InvalidInputException extends DomainException {
    public InvalidInputException(final String message) {
        super(message);
    }

    public InvalidInputException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
