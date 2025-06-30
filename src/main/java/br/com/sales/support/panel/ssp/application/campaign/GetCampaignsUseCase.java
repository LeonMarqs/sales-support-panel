package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.application.UseCase;
import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignFilter;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignStatusEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class GetCampaignsUseCase extends UseCase<GetCampaignsUseCase.Input, List<GetCampaignsUseCase.Output>> {

    private final CampaignRepository campaignRepository;

    public GetCampaignsUseCase(final CampaignRepository campaignRepository) {
        this.campaignRepository = Objects.requireNonNull(campaignRepository);
    }

    @Override
    public List<Output> execute(Input input) {
        final List<Campaign> campaignsByFilter = campaignRepository.getCampaignsByFilter(CampaignFilter.with(input));

        return campaignsByFilter.stream().map(campaign -> Output.builder()
                .id(campaign.getCampaignID().value())
                .name(campaign.getName().value())
                .budget(campaign.getBudget().value())
                .startDate(campaign.getStartDate())
                .endDate(campaign.getEndDate())
                .status(campaign.getStatus())
                .build()).toList();
    }

    public record Input(String name, LocalDate startDate, LocalDate endDate, BigDecimal budget, String status) {

    }

    @Builder
    public record Output(String id, String name, BigDecimal budget, LocalDate startDate, LocalDate endDate,
                         CampaignStatusEnum status) {
    }

}
