package br.com.sales.support.panel.ssp.infrastructure.graphql;

import br.com.sales.support.panel.ssp.application.campaign.*;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignID;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignPlatformEnum;
import br.com.sales.support.panel.ssp.domain.exceptions.InvalidInputException;
import br.com.sales.support.panel.ssp.infrastructure.dtos.GetCampaignsFilterDTO;
import br.com.sales.support.panel.ssp.infrastructure.graphql.dto.UpdateDetailsCampaignDTO;
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
    private final CompleteCampaignUseCase completeCampaignUseCase;
    private final ArchiveCampaignUseCase archiveCampaignUseCase;
    private final CancelCampaignUseCase cancelCampaignUseCase;
    private final PauseCampaignUseCase pauseCampaignUseCase;
    private final UpdateDetailsCampaignUseCase updateDetailsCampaignUseCase;

    public CampaignResolver(final GetCampaignsUseCase getCampaignsUseCase, final CreateCampaignUseCase createCampaignUseCase, final ActivateCampaignUseCase activateCampaignUseCase,
                            final CompleteCampaignUseCase completeCampaignUseCase, final ArchiveCampaignUseCase archiveCampaignUseCase,
                            final CancelCampaignUseCase cancelCampaignUseCase, final PauseCampaignUseCase pauseCampaignUseCase, final UpdateDetailsCampaignUseCase updateDetailsCampaignUseCase) {
        this.getCampaignsUseCase = Objects.requireNonNull(getCampaignsUseCase);
        this.createCampaignUseCase = Objects.requireNonNull(createCampaignUseCase);
        this.activateCampaignUseCase = Objects.requireNonNull(activateCampaignUseCase);
        this.completeCampaignUseCase = Objects.requireNonNull(completeCampaignUseCase);
        this.archiveCampaignUseCase = Objects.requireNonNull(archiveCampaignUseCase);
        this.cancelCampaignUseCase = Objects.requireNonNull(cancelCampaignUseCase);
        this.pauseCampaignUseCase = Objects.requireNonNull(pauseCampaignUseCase);
        this.updateDetailsCampaignUseCase = Objects.requireNonNull(updateDetailsCampaignUseCase);
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
        final ActivateCampaignUseCase.Input input = new ActivateCampaignUseCase.Input(new CampaignID(id));
        return activateCampaignUseCase.execute(input);
    }

    @MutationMapping
    public CompleteCampaignUseCase.Output completeCampaign(@Argument String id) {
        final CompleteCampaignUseCase.Input input = new CompleteCampaignUseCase.Input(new CampaignID(id));
        return completeCampaignUseCase.execute(input);
    }

    @MutationMapping
    public ArchiveCampaignUseCase.Output archiveCampaign(@Argument String id) {
        final ArchiveCampaignUseCase.Input input = new ArchiveCampaignUseCase.Input(new CampaignID(id));
        return archiveCampaignUseCase.execute(input);
    }

    @MutationMapping
    public CancelCampaignUseCase.Output cancelCampaign(@Argument String id) {
        final CancelCampaignUseCase.Input input = new CancelCampaignUseCase.Input(new CampaignID(id));
        return cancelCampaignUseCase.execute(input);
    }

    @MutationMapping
    public PauseCampaignUseCase.Output pauseCampaign(@Argument String id) {
        final PauseCampaignUseCase.Input input = new PauseCampaignUseCase.Input(new CampaignID(id));
        return pauseCampaignUseCase.execute(input);
    }

    @MutationMapping
    public UpdateDetailsCampaignUseCase.Output updateDetailsCampaign(@Argument String id, @Argument UpdateDetailsCampaignDTO details) {
        final UpdateDetailsCampaignUseCase.Input input = details.toInput(id);
        return updateDetailsCampaignUseCase.execute(input);
    }
}
