package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignFilter;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignID;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCampaignRepository implements CampaignRepository {

	private final Map<String, Campaign> campaignsById;

	public InMemoryCampaignRepository() {
		this.campaignsById = new HashMap<>();
	}

	@Override
	public Optional<Campaign> getCampaignById(final CampaignID id) {
		return Optional.ofNullable(campaignsById.get(id.id()));
	}

	@Override
	public List<Campaign> getCampaignsByFilter(final CampaignFilter filter) {

		List<Campaign> campaignsFiltered = campaignsById.values().stream().toList();

		if (filter.hasName()) {
			campaignsFiltered = campaignsFiltered.stream()
					.filter(campaign -> campaign.getName().value().contains(filter.name().toUpperCase())).toList();
		}

		if (filter.hasStartDate()) {
			campaignsFiltered = campaignsFiltered.stream()
					.filter(campaign -> campaign.getStartDate().isEqual(filter.startDate()) || campaign.getStartDate()
							.isAfter(filter.startDate())).toList();
		}

		if (filter.hasEndDate()) {
			campaignsFiltered = campaignsFiltered.stream()
					.filter(campaign -> campaign.getStartDate().isBefore(filter.endDate()) || campaign.getStartDate()
							.isEqual(filter.endDate())).toList();
		}

		if (filter.hasBudget()) {
			campaignsFiltered = campaignsFiltered.stream()
					.filter(campaign -> campaign.getBudget().value().compareTo(filter.budget()) >= 0).toList();
		}

		if (campaignsFiltered.isEmpty()) {
			return List.of();
		}

		return campaignsFiltered;
	}

	@Override
	public Campaign create(final Campaign campaign) {
		campaignsById.put(campaign.getCampaignID().id(), campaign);
		return campaign;
	}

}
