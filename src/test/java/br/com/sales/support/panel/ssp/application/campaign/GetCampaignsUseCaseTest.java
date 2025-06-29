package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetCampaignsUseCaseTest {

	@Test
	void shouldReturnEmptyListWhenNoCampaignsExist() {

		final CampaignRepository campaignRepository = new InMemoryCampaignRepository();

		GetCampaignsUseCase useCase = new GetCampaignsUseCase(campaignRepository);
		GetCampaignsUseCase.Input input = new GetCampaignsUseCase.Input(null, null, null, null);
		var result = useCase.execute(input);
		assertTrue(result.isEmpty(), "Expected empty list when no campaigns exist");
	}

	@Test
	void shouldReturnFilteredCampaignsByName() {

		final CampaignRepository campaignRepository = new InMemoryCampaignRepository();

		campaignRepository.create(Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now()));
		campaignRepository.create(Campaign.newCampaign("Campaign 2", BigDecimal.valueOf(1000.00), LocalDate.now()));
		campaignRepository.create(Campaign.newCampaign("Test Campaign 2", BigDecimal.valueOf(1000.00), LocalDate.now()));

		GetCampaignsUseCase useCase = new GetCampaignsUseCase(campaignRepository);
		GetCampaignsUseCase.Input input = new GetCampaignsUseCase.Input("test", null, null, null);
		var result = useCase.execute(input);
		assertEquals(2, result.size());
	}

}
