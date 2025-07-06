package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.domain.campaign.Campaign;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignPlatformEnum;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;
import br.com.sales.support.panel.ssp.domain.campaign.CampaignStatusEnum;
import br.com.sales.support.panel.ssp.domain.exceptions.DomainException;
import br.com.sales.support.panel.ssp.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class ArchiveCampaignUseCaseTest {

    @Test
    void shouldArchiveCampaignSuccessfully() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        inMemoryCampaignRepository.create(newCampaign);

        final String campaignID = newCampaign.getCampaignID().value();

        ArchiveCampaignUseCase useCase = new ArchiveCampaignUseCase(inMemoryCampaignRepository);
        ArchiveCampaignUseCase.Input input = new ArchiveCampaignUseCase.Input(campaignID);

        // Act
        ArchiveCampaignUseCase.Output output = useCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(CampaignStatusEnum.ARCHIVED, output.status());
    }

    @Test
    void shouldThrowErrorWhenCampaignIsNotFound() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        inMemoryCampaignRepository.create(newCampaign);

        ArchiveCampaignUseCase useCase = new ArchiveCampaignUseCase(inMemoryCampaignRepository);
        ArchiveCampaignUseCase.Input input = new ArchiveCampaignUseCase.Input("123");

        // Assert
        assertThrows(NotFoundException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    void shouldThrowErrorWhenCampaignIsAlreadyArchived() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        final Campaign createdCampaign = inMemoryCampaignRepository.create(newCampaign);

        ArchiveCampaignUseCase useCase = new ArchiveCampaignUseCase(inMemoryCampaignRepository);
        ArchiveCampaignUseCase.Input input = new ArchiveCampaignUseCase.Input(createdCampaign.getCampaignID().value());
        useCase.execute(input);

        // Assert
        assertThrows(DomainException.class, () -> {
            useCase.execute(input);
        });
    }
}
