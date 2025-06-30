package br.com.sales.support.panel.ssp.infrastructure.graphql;

import br.com.sales.support.panel.ssp.application.campaign.CreateCampaignUseCase;
import br.com.sales.support.panel.ssp.application.campaign.GetCampaignsUseCase;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignPlatformEnum;
import br.com.sales.support.panel.ssp.infrastructure.dtos.GetCampaignsFilterDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CampaignResolver {

    private final GetCampaignsUseCase getCampaignsUseCase;
    private final CreateCampaignUseCase createCampaignUseCase;

    public CampaignResolver(final GetCampaignsUseCase getCampaignsUseCase, final CreateCampaignUseCase createCampaignUseCase) {
        this.getCampaignsUseCase = Objects.requireNonNull(getCampaignsUseCase);
        this.createCampaignUseCase = Objects.requireNonNull(createCampaignUseCase);
    }

    @QueryMapping
    public List<GetCampaignsUseCase.Output> getCampaigns(@Argument Optional<GetCampaignsFilterDTO> filter) {
        return getCampaignsUseCase.execute(
                new GetCampaignsUseCase.Input(
                        filter.map(GetCampaignsFilterDTO::name).orElse(null),
                        filter.map(GetCampaignsFilterDTO::startDate).orElse(null),
                        filter.map(GetCampaignsFilterDTO::endDate).orElse(null),
                        filter.map(GetCampaignsFilterDTO::budget).orElse(null),
                        filter.map(GetCampaignsFilterDTO::status).orElse(null)
                )
        );
    }

    @MutationMapping
    public CreateCampaignUseCase.Output createCampaign(@Argument String name, @Argument String startDate, @Argument String budget,
                                                       @Argument Boolean active, @Argument CampaignPlatformEnum platform) {
        return createCampaignUseCase.execute(
                new CreateCampaignUseCase.Input(name, startDate != null ? LocalDate.parse(startDate) : null,
                        budget != null ? new BigDecimal(budget) : null, active, platform));
    }

}
