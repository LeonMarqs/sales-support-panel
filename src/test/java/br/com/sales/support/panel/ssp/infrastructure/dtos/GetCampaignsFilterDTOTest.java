package br.com.sales.support.panel.ssp.infrastructure.dtos;

import br.com.sales.support.panel.ssp.domain.campaign.CampaignStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;


class GetCampaignsFilterDTOTest {
    @Test
    void testGetCampaignsFilterDTO() {
        final String name = "test";
        final LocalDate startDate = LocalDate.now();
        final LocalDate endDate = startDate.plusDays(10);
        final BigDecimal budget = new BigDecimal(500);
        final CampaignStatusEnum status = CampaignStatusEnum.ACTIVE;

        GetCampaignsFilterDTO filter = new GetCampaignsFilterDTO(name, startDate, endDate, budget, status);

        Assertions.assertEquals(name, filter.name());
        Assertions.assertEquals(startDate, filter.startDate());
        Assertions.assertEquals(endDate, filter.endDate());
        Assertions.assertEquals(budget, filter.budget());
        Assertions.assertEquals(status, filter.status());
    }
}
