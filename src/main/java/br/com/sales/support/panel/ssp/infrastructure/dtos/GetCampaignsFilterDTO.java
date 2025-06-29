package br.com.sales.support.panel.ssp.infrastructure.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GetCampaignsFilterDTO(String name, LocalDate startDate, LocalDate endDate, BigDecimal budget) {

}
