package br.com.sales.support.panel.ssp.domain.campaign;

import br.com.sales.support.panel.ssp.domain.common.Money;
import br.com.sales.support.panel.ssp.domain.common.Name;
import br.com.sales.support.panel.ssp.domain.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Campaign {

	private final CampaignID campaignID;
	private Name name;
	private Money budget;
	private LocalDate startDate;

	public Campaign(final CampaignID campaignID, final String name, final BigDecimal budget, final LocalDate startDate) {

		if (campaignID == null) {
			throw new ValidationException("Campaign ID cannot be null");
		}

		this.campaignID = campaignID;
		this.name = new Name(name);
		this.budget = new Money(budget);
		this.startDate = startDate;
	}

	public static Campaign newCampaign(final String name, final BigDecimal budget, final LocalDate startDate) {
		return new Campaign(CampaignID.unique(), name, budget, startDate);
	}

	public Name getName() {
		return name;
	}

	public Money getBudget() {
		return budget;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public CampaignID getCampaignID() {
		return campaignID;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		final Campaign campaign = (Campaign) o;
		return Objects.equals(campaignID, campaign.campaignID);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(campaignID);
	}

}
