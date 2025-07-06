package br.com.sales.support.panel.ssp.infrastructure.graphql;

import br.com.sales.support.panel.ssp.application.campaign.ActivateCampaignUseCase;
import br.com.sales.support.panel.ssp.application.campaign.CreateCampaignUseCase;
import br.com.sales.support.panel.ssp.application.campaign.GetCampaignsUseCase;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignPlatformEnum;
import br.com.sales.support.panel.ssp.domain.exceptions.InvalidInputException;
import br.com.sales.support.panel.ssp.infrastructure.dtos.GetCampaignsFilterDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CampaignResolver {

    private final GetCampaignsUseCase getCampaignsUseCase;
    private final CreateCampaignUseCase createCampaignUseCase;
    private final ActivateCampaignUseCase activateCampaignUseCase;

    public CampaignResolver(final GetCampaignsUseCase getCampaignsUseCase, final CreateCampaignUseCase createCampaignUseCase, final ActivateCampaignUseCase activateCampaignUseCase) {
        this.getCampaignsUseCase = Objects.requireNonNull(getCampaignsUseCase);
        this.createCampaignUseCase = Objects.requireNonNull(createCampaignUseCase);
        this.activateCampaignUseCase = Objects.requireNonNull(activateCampaignUseCase);
    }

    @QueryMapping
    public List<GetCampaignsUseCase.Output> getCampaigns(@Argument Optional<GetCampaignsFilterDTO> filter) {

        final GetCampaignsUseCase.Input input = filter.map(f -> new GetCampaignsUseCase.Input(
                f.name(), f.startDate(), f.endDate(), f.budget(), f.status()
        )).orElse(null);

        return getCampaignsUseCase.execute(input);
    }

    @MutationMapping
    public CreateCampaignUseCase.Output createCampaign(@Argument String name, @Argument String startDate, @Argument String budget, @Argument CampaignPlatformEnum platform) {
        try {
            final LocalDate parsedStartDate = startDate != null ? LocalDate.parse(startDate) : null;
            final BigDecimal parsedBudget = budget != null ? new BigDecimal(budget) : null;

            final var input = new CreateCampaignUseCase.Input(name, parsedStartDate, parsedBudget, platform);
            return createCampaignUseCase.execute(input);
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new InvalidInputException("Invalid format for date or budget.", e);
        }
    }

    @MutationMapping
    public ActivateCampaignUseCase.Output activateCampaign(@Argument String id) {
        final ActivateCampaignUseCase.Input input = new ActivateCampaignUseCase.Input(id);
        return activateCampaignUseCase.execute(input);
    }
}
