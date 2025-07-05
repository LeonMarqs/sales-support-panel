package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignFilter;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignID;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryCampaignRepository implements CampaignRepository {

    private final Map<String, Campaign> campaignsById;

    public InMemoryCampaignRepository() {
        this.campaignsById = new HashMap<>();
    }

    @Override
    public Optional<Campaign> getCampaignById(final CampaignID id) {
        return Optional.ofNullable(campaignsById.get(id.value()));
    }

    @Override
    public List<Campaign> getCampaignsByFilter(final CampaignFilter filter) {

        Stream<Campaign> campaignsFiltered = campaignsById.values().stream();

        if (filter.hasName()) {
            campaignsFiltered = campaignsFiltered
                    .filter(campaign -> campaign.getName().value().contains(filter.name().toUpperCase()));
        }

        if (filter.hasStartDate()) {
            campaignsFiltered = campaignsFiltered
                    .filter(campaign -> campaign.getStartDate().isEqual(filter.startDate()) || campaign.getStartDate()
                            .isAfter(filter.startDate()));
        }

        if (filter.hasEndDate()) {
            campaignsFiltered = campaignsFiltered
                    .filter(campaign -> campaign.getStartDate().isBefore(filter.endDate()) || campaign.getStartDate()
                            .isEqual(filter.endDate()));
        }

        if (filter.hasBudget()) {
            campaignsFiltered = campaignsFiltered
                    .filter(campaign -> campaign.getBudget().value().compareTo(filter.budget()) >= 0);
        }

        if (filter.hasStatus()) {
            campaignsFiltered = campaignsFiltered
                    .filter(campaign -> campaign.getStatus().equals(filter.status()));
        }

        return campaignsFiltered.toList();
    }

    @Override
    public Campaign create(final Campaign campaign) {
        campaignsById.put(campaign.getCampaignID().value(), campaign);
        return campaign;
    }

}
