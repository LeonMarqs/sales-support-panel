package br.com.sales.support.panel.ssp.infrastructure.jpa.entities;

import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "Campaign")
@Table(name = "campaigns")
@Getter
@Setter
public class CampaignJPA {

	@Id
	@Column(name = "id")
	private UUID id;
	@Column(name = "name")
	private String name;
	@Column(name = "budget")
	private BigDecimal budget;
	@Column(name = "start_date")
	private LocalDate startDate;

	public CampaignJPA() {
	}

	public CampaignJPA(final UUID id, final String name, final BigDecimal budget, final LocalDate startDate) {
		this.id = id;
		this.name = name;
		this.budget = budget;
		this.startDate = startDate;
	}

	public static CampaignJPA of(final Campaign campaign) {
		return new CampaignJPA(UUID.fromString(campaign.getCampaignID().value()), campaign.getName().value(),
				campaign.getBudget().value(), campaign.getStartDate());
	}

	public Campaign toCampaign() {
		return new Campaign(CampaignID.with(this.id.toString()), this.name, this.budget, this.startDate);
	}

}
