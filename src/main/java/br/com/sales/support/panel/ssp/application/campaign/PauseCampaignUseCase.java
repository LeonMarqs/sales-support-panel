package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.application.UseCase;
import br.com.sales.support.panel.ssp.domain.campaign.*;
import br.com.sales.support.panel.ssp.domain.exceptions.NotFoundException;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class PauseCampaignUseCase extends UseCase<PauseCampaignUseCase.Input, PauseCampaignUseCase.Output> {

    private final CampaignRepository campaignRepository;

    public PauseCampaignUseCase(final CampaignRepository campaignRepository) {
        this.campaignRepository = Objects.requireNonNull(campaignRepository);
    }

    @Override
    public Output execute(Input input) {
        final Campaign campaign = campaignRepository.getCampaignById(input.id).orElseThrow(() -> new NotFoundException("Campaign not found with ID: " + input.id));
        campaign.pause();

        final Campaign updatedCampaign = campaignRepository.save(campaign);
        return Output.from(updatedCampaign);
    }

    public record Input(CampaignID id) {

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
