package br.com.sales.support.panel.ssp.infrastructure.repositories;

import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignFilter;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignID;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;
import br.com.sales.support.panel.ssp.infrastructure.jpa.entities.CampaignJPA;
import br.com.sales.support.panel.ssp.infrastructure.jpa.repositories.CampaignJPARepository;
import br.com.sales.support.panel.ssp.infrastructure.jpa.repositories.specifications.CampaignJPASpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CampaignDatabaseRepository implements CampaignRepository {

	private final CampaignJPARepository campaignJPARepository;

	public CampaignDatabaseRepository(final CampaignJPARepository campaignJPARepository) {
		this.campaignJPARepository = Objects.requireNonNull(campaignJPARepository);
	}

	@Override
	public Optional<Campaign> getCampaignById(final CampaignID id) {
		Objects.requireNonNull(id, "Campaign ID must not be null");
		return campaignJPARepository.findById(UUID.fromString(id.value())).map(CampaignJPA::toCampaign);
	}

	@Override
	public List<Campaign> getCampaignsByFilter(final CampaignFilter filter) {
		Specification<CampaignJPA> specification = CampaignJPASpecification.byFilter(filter);
		return campaignJPARepository.findAll(specification).stream().map(CampaignJPA::toCampaign).toList();
	}

	@Override
	@Transactional
	public Campaign create(final Campaign campaign) {
		return campaignJPARepository.save(CampaignJPA.of(campaign)).toCampaign();
	}

}
