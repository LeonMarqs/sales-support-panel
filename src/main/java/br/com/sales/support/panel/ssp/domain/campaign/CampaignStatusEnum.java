package br.com.sales.support.panel.ssp.domain.campaign;

public enum CampaignStatusEnum {
    DRAFT, ACTIVE, PAUSED, COMPLETED, ARCHIVED;

    public static CampaignStatusEnum getInitialStatus(final Boolean active) {
        if (active == null || !active) {
            return DRAFT;
        }
        return ACTIVE;
    }
}
