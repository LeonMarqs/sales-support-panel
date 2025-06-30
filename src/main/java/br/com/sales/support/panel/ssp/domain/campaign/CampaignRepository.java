package br.com.sales.support.panel.ssp.domain.campaign;

import java.util.List;
import java.util.Optional;

public interface CampaignRepository {

    Optional<Campaign> getCampaignById(CampaignID id);

    List<Campaign> getCampaignsByFilter(CampaignFilter filter);

    Campaign create(Campaign campaign);

}
