package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.application.UseCase;
import br.com.sales.support.panel.ssp.domain.campaign.*;
import br.com.sales.support.panel.ssp.domain.common.Money;
import br.com.sales.support.panel.ssp.domain.common.Name;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class GetCampaignsUseCase extends UseCase<GetCampaignsUseCase.Input, List<GetCampaignsUseCase.Output>> {

    private final CampaignRepository campaignRepository;

    public GetCampaignsUseCase(final CampaignRepository campaignRepository) {
        this.campaignRepository = Objects.requireNonNull(campaignRepository);
    }

    @Override
    public List<Output> execute(Input input) {
        final List<Campaign> campaignsByFilter = campaignRepository.getCampaignsByFilter(CampaignFilter.with(input));
        return campaignsByFilter.stream().map(Output::from).toList();
    }

    public record Input(String name, LocalDate startDate, LocalDate endDate, BigDecimal budget,
                        CampaignStatusEnum status) {

    }

    @Builder
    public record Output(CampaignID id, String name, BigDecimal budget, LocalDate startDate, LocalDate endDate,
                         CampaignStatusEnum status, CampaignPlatformEnum platform) {

        public static Output from(final Campaign campaign) {
            return Output.builder()
                    .id(campaign.getCampaignID())
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
