package br.com.sales.support.panel.ssp.infrastructure.dtos;

import br.com.sales.support.panel.ssp.domain.campaign.CampaignStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetCampaignsFilterDTO(String name, LocalDate startDate, LocalDate endDate, BigDecimal budget,
                                    CampaignStatusEnum status) {

}
