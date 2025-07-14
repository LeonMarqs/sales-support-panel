package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.application.UseCase;
import br.com.sales.support.panel.ssp.domain.campaign.*;
import br.com.sales.support.panel.ssp.domain.common.Money;
import br.com.sales.support.panel.ssp.domain.common.Name;
import br.com.sales.support.panel.ssp.domain.exceptions.NotFoundException;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class UpdateDetailsCampaignUseCase extends UseCase<UpdateDetailsCampaignUseCase.Input, UpdateDetailsCampaignUseCase.Output> {

    private final CampaignRepository campaignRepository;

    public UpdateDetailsCampaignUseCase(final CampaignRepository campaignRepository) {
        this.campaignRepository = Objects.requireNonNull(campaignRepository);
    }

    @Override
    public Output execute(Input input) {
        final Campaign campaign = campaignRepository.getCampaignById(input.id).orElseThrow(() -> new NotFoundException("Campaign not found with ID: " + input.id));

        input.name.ifPresent((name) -> campaign.changeName(new Name(name)));
        input.budget.ifPresent((budget) -> campaign.changeBudget(new Money(budget)));
        input.startDate.ifPresent(campaign::changeStartDate);
        input.endDate.ifPresent(campaign::changeEndDate);
        input.platform.ifPresent(campaign::changePlatform);

        final Campaign updatedCampaign = campaignRepository.save(campaign);
        return Output.from(updatedCampaign);
    }

    public record Input(CampaignID id,
                        Optional<String> name,
                        Optional<BigDecimal> budget,
                        Optional<LocalDate> startDate,
                        Optional<LocalDate> endDate,
                        Optional<CampaignPlatformEnum> platform) {

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
