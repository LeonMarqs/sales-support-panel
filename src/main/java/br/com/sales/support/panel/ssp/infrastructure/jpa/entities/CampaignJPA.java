package br.com.sales.support.panel.ssp.infrastructure.jpa.entities;

import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignID;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignPlatformEnum;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignStatusEnum;
import br.com.sales.support.panel.ssp.domain.common.Money;
import br.com.sales.support.panel.ssp.domain.common.Name;
import jakarta.persistence.*;
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
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CampaignStatusEnum status;
    @Column(name = "platform")
    @Enumerated(EnumType.STRING)
    private CampaignPlatformEnum platform;

    public CampaignJPA() {
    }

    public static CampaignJPA of(final Campaign campaign) {
        final var jpaEntity = new CampaignJPA();
        jpaEntity.setId(UUID.fromString(campaign.getCampaignID().value()));
        jpaEntity.setName(campaign.getName().value());
        jpaEntity.setBudget(campaign.getBudget().value());
        jpaEntity.setStartDate(campaign.getStartDate());
        jpaEntity.setEndDate(campaign.getEndDate());
        jpaEntity.setStatus(campaign.getStatus());
        jpaEntity.setPlatform(campaign.getPlatform());
        return jpaEntity;
    }

    public Campaign toCampaign() {
        return Campaign.builder()
                .campaignID(CampaignID.with(this.id.toString()))
                .name(new Name(this.name))
                .budget(new Money(this.budget))
                .startDate(this.startDate)
                .endDate(this.endDate)
                .status(this.status)
                .platform(this.platform)
                .build();
    }

}
