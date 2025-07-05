package br.com.sales.support.panel.ssp.domain.campaign;

import br.com.sales.support.panel.ssp.domain.exceptions.DomainException;

import java.util.UUID;

public record CampaignID(String value) {

    public CampaignID {
        if (value == null) {
            throw new DomainException("Campaign ID cannot be empty");
        }
    }

    public static CampaignID unique() {
        return new CampaignID(UUID.randomUUID().toString());
    }

    public static CampaignID with(final String value) {
        try {
            return new CampaignID(UUID.fromString(value).toString());
        } catch (IllegalArgumentException exception) {
            throw new DomainException("Invalid ID for campaign");
        }
    }

}
