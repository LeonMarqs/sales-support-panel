package br.com.sales.support.panel.ssp.domain.campaign;

import br.com.sales.support.panel.ssp.domain.common.Money;
import br.com.sales.support.panel.ssp.domain.common.Name;
import br.com.sales.support.panel.ssp.domain.exceptions.DomainException;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
public class Campaign {

    private final CampaignID campaignID;
    private final Name name;
    private final Money budget;
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

    public static Campaign newCampaign(final String name, final BigDecimal budget, final LocalDate startDate, final CampaignPlatformEnum platform) {
        return Campaign.builder()
                .campaignID(CampaignID.unique())
                .name(new Name(name))
                .budget(new Money(budget))
                .startDate(startDate)
                .platform(platform != null ? platform : CampaignPlatformEnum.OTHER)
                .status(CampaignStatusEnum.DRAFT)
                .build();
    }

    public void complete() {
        if (!canBeCompleted()) {
            throw new DomainException("Campaign status does not allow completion");
        }

        this.status = CampaignStatusEnum.COMPLETED;
        this.endDate = LocalDate.now();
    }

    public void pause() {
        if (!canBePaused()) {
            throw new DomainException("Campaign status does not allow pausing");
        }

        this.status = CampaignStatusEnum.PAUSED;
    }

    public void activate() {
        if (!canBeActivated()) {
            throw new DomainException("Campaign status does not allow activation");
        }

        this.status = CampaignStatusEnum.ACTIVE;
        this.startDate = LocalDate.now();
    }

    public void archive() {
        if (!canBeArchived()) {
            throw new DomainException("Campaign status does not allow archiving");
        }

        this.status = CampaignStatusEnum.ARCHIVED;
    }

    public void cancel() {
        if (!canBeCancelled()) {
            throw new DomainException("Campaign status does not allow cancellation");
        }

        this.status = CampaignStatusEnum.CANCELLED;
        this.endDate = LocalDate.now();
    }


    private void validate() {
        if (campaignID == null) {
            throw new DomainException("Campaign ID cannot be null");
        }

        if (status == null) {
            throw new DomainException("Campaign status cannot be null or empty");
        }
    }

    private boolean canBeCompleted() {
        return this.status == CampaignStatusEnum.ACTIVE || this.status == CampaignStatusEnum.DRAFT ||
                this.status == CampaignStatusEnum.COMPLETED;
    }

    private boolean canBePaused() {
        return this.status == CampaignStatusEnum.ACTIVE || this.status == CampaignStatusEnum.DRAFT;
    }

    private boolean canBeActivated() {
        return this.status == CampaignStatusEnum.DRAFT || this.status == CampaignStatusEnum.PAUSED;
    }

    private boolean canBeArchived() {
        return this.status != CampaignStatusEnum.COMPLETED && this.status != CampaignStatusEnum.CANCELLED && this.status != CampaignStatusEnum.ARCHIVED;
    }

    private boolean canBeCancelled() {
        return this.status == CampaignStatusEnum.DRAFT || this.status == CampaignStatusEnum.PAUSED ||
                this.status == CampaignStatusEnum.ACTIVE;
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
