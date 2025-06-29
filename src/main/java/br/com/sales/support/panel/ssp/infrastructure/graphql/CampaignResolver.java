package br.com.sales.support.panel.ssp.infrastructure.graphql;

import br.com.sales.support.panel.ssp.application.campaign.GetCampaignsUseCase;
import br.com.sales.support.panel.ssp.infrastructure.dtos.GetCampaignsFilterDTO;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CampaignResolver {

	private final GetCampaignsUseCase getCampaignsUseCase;

	public CampaignResolver(final GetCampaignsUseCase getCampaignsUseCase) {
		this.getCampaignsUseCase = Objects.requireNonNull(getCampaignsUseCase);
	}

	@QueryMapping
	public List<GetCampaignsUseCase.Output> getCampaigns(
			@Argument Optional<GetCampaignsFilterDTO> filter
	) {
		return getCampaignsUseCase.execute(new GetCampaignsUseCase.Input(
				filter.map(GetCampaignsFilterDTO::name).orElse(null),
				filter.map(GetCampaignsFilterDTO::startDate).orElse(null),
				filter.map(GetCampaignsFilterDTO::endDate).orElse(null),
				filter.map(GetCampaignsFilterDTO::budget).orElse(null)
		));
	}

}
