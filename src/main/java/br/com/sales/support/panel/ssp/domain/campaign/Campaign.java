package br.com.sales.support.panel.ssp.domain.campaign;

import br.com.sales.support.panel.ssp.domain.common.Money;
import br.com.sales.support.panel.ssp.domain.common.Name;
import br.com.sales.support.panel.ssp.domain.exceptions.ValidationException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Campaign {

    private final CampaignID campaignID;
    private Name name;
    private Money budget;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampaignStatusEnum status;
    private CampaignPlatformEnum platform;

    @Builder
    public Campaign(final CampaignID campaignID, final Name name, final Money budget, final LocalDate startDate,
                    final LocalDate endDate, final CampaignStatusEnum status, final CampaignPlatformEnum platform) {
        this.campaignID = campaignID;
        this.name = name;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
        this.platform = platform;
        this.status = status;
        this.validate();
    }

    public static Campaign newCampaign(final String name, final BigDecimal budget, final LocalDate startDate, Boolean active, final CampaignPlatformEnum platform) {
        return Campaign.builder()
                .campaignID(CampaignID.unique())
                .name(new Name(name))
                .budget(new Money(budget))
                .startDate(startDate)
                .platform(CampaignPlatformEnum.getInicialPlatform(platform))
                .status(CampaignStatusEnum.getInitialStatus(active))
                .build();
    }

    private void validate() {
        if (campaignID == null) {
            throw new ValidationException("Campaign ID cannot be null");
        }

        if (status == null) {
            throw new ValidationException("Campaign status cannot be null or empty");
        }
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
