package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.application.UseCase;
import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;

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
		final Campaign newCampaign = Campaign.newCampaign(input.name(), input.budget(), input.startDate());
		final Campaign createdCampaign = campaignRepository.create(newCampaign);
		return new Output(
				createdCampaign.getCampaignID().value(),
				createdCampaign.getName().value(),
				createdCampaign.getBudget().value(),
				createdCampaign.getStartDate()
		);
	}

	public record Input(String name, LocalDate startDate, BigDecimal budget) {

	}

	public record Output(String id, String name, BigDecimal budget, LocalDate startDate) {

	}

}
