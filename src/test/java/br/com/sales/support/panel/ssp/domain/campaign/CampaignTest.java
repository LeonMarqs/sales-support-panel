package br.com.sales.support.panel.ssp.domain.campaign;

import br.com.sales.support.panel.ssp.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CampaignTest {

    @Test
    void shouldCreateCampaignWithValidData() {
        final String name = "Test Campaign";
        final BigDecimal budget = BigDecimal.valueOf(1000.00);
        final Campaign campaign = Campaign.newCampaign(name, budget, LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);

        assertNotNull(campaign);
        assertNotNull(campaign.getCampaignID());
        assertEquals(budget, campaign.getBudget().value());
    }

    @Test
    void shouldThrowExceptionForNullCampaignName() {
        final BigDecimal budget = BigDecimal.valueOf(1000.00);
        assertThrows(DomainException.class, () -> Campaign.newCampaign(null, budget, LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS));
    }

    @Test
    void shouldThrowExceptionForNegativeBudget() {
        final BigDecimal budget = BigDecimal.valueOf(-2000);
        assertThrows(DomainException.class, () -> Campaign.newCampaign("Test campaign", budget, LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS));
    }

    @Test
    void shouldActivateCampaign() {
        final Campaign campaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        campaign.activate();

        assertEquals(CampaignStatusEnum.ACTIVE, campaign.getStatus());
        assertNotNull(campaign.getCampaignID().value());
    }

    @Test
    void shouldPauseCampaign() {
        final Campaign campaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        campaign.pause();

        assertEquals(CampaignStatusEnum.PAUSED, campaign.getStatus());
        assertNotNull(campaign.getCampaignID().value());
    }

    @Test
    void shouldCancelCampaign() {
        final Campaign campaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        campaign.cancel();

        assertEquals(CampaignStatusEnum.CANCELLED, campaign.getStatus());
        assertNotNull(campaign.getCampaignID().value());
    }

    @Test
    void shouldArchiveCampaign() {
        final Campaign campaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        campaign.archive();

        assertEquals(CampaignStatusEnum.ARCHIVED, campaign.getStatus());
        assertNotNull(campaign.getCampaignID().value());
    }

    @Test
    void shouldCompleteCampaign() {
        final Campaign campaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        campaign.complete();

        assertEquals(CampaignStatusEnum.COMPLETED, campaign.getStatus());
        assertNotNull(campaign.getCampaignID().value());
    }

}
