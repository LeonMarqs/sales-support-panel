package br.com.sales.support.panel.ssp.infrastructure.repositories;

import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignFilter;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignID;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class CampaignDatabaseRepository implements CampaignRepository {

	@Override
	public Optional<Campaign> getCampaignById(final CampaignID id) {
		return Optional.empty(); // TODO
	}

	@Override
	public List<Campaign> getCampaignsByFilter(final CampaignFilter filter) {
		// TODO
		return List.of(Campaign.newCampaign("Test campaign", BigDecimal.valueOf(50000), LocalDate.now()));
	}

	@Override
	public Campaign create(final Campaign campaign) {
		return null; // TODO
	}

}
