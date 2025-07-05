package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.application.UseCase;
import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignPlatformEnum;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignStatusEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class CreateCampaignUseCase extends UseCase<CreateCampaignUseCase.Input, CreateCampaignUseCase.Output> {

    private final CampaignRepository campaignRepository;

    public CreateCampaignUseCase(final CampaignRepository campaignRepository) {
        this.campaignRepository = Objects.requireNonNull(campaignRepository);
    }

    @Override
    public Output execute(Input input) {
        final Campaign newCampaign = Campaign.newCampaign(input.name(), input.budget(), input.startDate(), input.platform());
        final Campaign createdCampaign = campaignRepository.create(newCampaign);
        return Output.from(createdCampaign);
    }

    public record Input(String name, LocalDate startDate, BigDecimal budget,
                        CampaignPlatformEnum platform) {

    }

    @Builder
    public record Output(String id, String name, BigDecimal budget, LocalDate startDate, LocalDate endDate,
                         CampaignStatusEnum status, CampaignPlatformEnum platform) {

        public static Output from(final Campaign campaign) {
            return Output.builder()
                    .id(campaign.getCampaignID().value())
                    .name(campaign.getName().value())
                    .budget(campaign.getBudget().value())
                    .startDate(campaign.getStartDate())
                    .endDate(campaign.getEndDate())
                    .status(campaign.getStatus())
                    .platform(campaign.getPlatform())
                    .build();
        }

    }

}
