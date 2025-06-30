package br.com.sales.support.panel.ssp.domain.campaign;

import br.com.sales.support.panel.ssp.application.campaign.GetCampaignsUseCase;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CampaignFilter(String name, LocalDate startDate, LocalDate endDate, BigDecimal budget) {

    public static CampaignFilter with(GetCampaignsUseCase.Input input) {
        return new CampaignFilter(input.name(), input.startDate(), input.endDate(), input.budget());
    }

    public boolean hasName() {
        return name != null && !name.isBlank();
    }

    public boolean hasStartDate() {
        return startDate != null;
    }

    public boolean hasEndDate() {
        return endDate != null;
    }

    public boolean hasBudget() {
        return budget != null;
    }

}
