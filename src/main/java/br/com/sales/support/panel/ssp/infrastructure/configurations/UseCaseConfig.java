package br.com.sales.support.panel.ssp.infrastructure.configurations;

import br.com.sales.support.panel.ssp.application.campaign.*;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {

    private final CampaignRepository campaignRepository;

    public UseCaseConfig(final CampaignRepository campaignRepository) {
        this.campaignRepository = Objects.requireNonNull(campaignRepository);
    }

    @Bean
    public GetCampaignsUseCase getCampaignsUseCase() {
        return new GetCampaignsUseCase(campaignRepository);
    }

    @Bean
    public CreateCampaignUseCase createCampaignUseCase() {
        return new CreateCampaignUseCase(campaignRepository);
    }

    @Bean
    public ActivateCampaignUseCase activateCampaignUseCase() {
        return new ActivateCampaignUseCase(campaignRepository);
    }

    @Bean
    public CompleteCampaignUseCase completeCampaignUseCase() {
        return new CompleteCampaignUseCase(campaignRepository);
    }

    @Bean
    public ArchiveCampaignUseCase archiveCampaignUseCase() {
        return new ArchiveCampaignUseCase(campaignRepository);
    }

    @Bean
    public CancelCampaignUseCase cancelCampaignUseCase() {
        return new CancelCampaignUseCase(campaignRepository);
    }

    @Bean
    public PauseCampaignUseCase pauseCampaignUseCase() {
        return new PauseCampaignUseCase(campaignRepository);
    }

}
