package br.com.sales.support.panel.ssp.domain.campaign;

import br.com.sales.support.panel.ssp.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CampaignTest {

    @Test
    void shouldCreateCampaignWithValidData() {
        final String name = "Test Campaign";
        final BigDecimal budget = BigDecimal.valueOf(1000.00);
        final Campaign campaign = Campaign.newCampaign(name, budget, LocalDate.now());

        assertNotNull(campaign);
        assertNotNull(campaign.getCampaignID());
        assertEquals(budget, campaign.getBudget().value());
    }

    @Test
    void shouldThrowExceptionForNullCampaignName() {
        final BigDecimal budget = BigDecimal.valueOf(1000.00);
        assertThrows(ValidationException.class, () -> Campaign.newCampaign(null, budget, LocalDate.now()));
    }

    @Test
    void shouldThrowExceptionForNegativeBudget() {
        final BigDecimal budget = BigDecimal.valueOf(-2000);
        assertThrows(ValidationException.class, () -> Campaign.newCampaign("Test campaign", budget, LocalDate.now()));
    }

}
