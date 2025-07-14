package br.com.sales.support.panel.ssp.infrastructure.graphql.dto;

import br.com.sales.support.panel.ssp.application.campaign.UpdateDetailsCampaignUseCase;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignID;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignPlatformEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public record UpdateDetailsCampaignDTO(Optional<String> name,
                                       Optional<BigDecimal> budget,
                                       Optional<LocalDate> startDate,
                                       Optional<LocalDate> endDate,
                                       Optional<CampaignPlatformEnum> platform) {

    public UpdateDetailsCampaignUseCase.Input toInput(final String id) {
        return new UpdateDetailsCampaignUseCase.Input(
                CampaignID.with(id),
                name,
                budget,
                startDate,
                endDate,
                platform
        );
    }
}
