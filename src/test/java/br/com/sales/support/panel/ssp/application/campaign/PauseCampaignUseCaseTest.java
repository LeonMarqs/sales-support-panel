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


class PauseCampaignUseCaseTest {

    @Test
    void shouldPauseCampaignSuccessfully() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        inMemoryCampaignRepository.create(newCampaign);

        final String campaignID = newCampaign.getCampaignID().value();

        PauseCampaignUseCase useCase = new PauseCampaignUseCase(inMemoryCampaignRepository);
        PauseCampaignUseCase.Input input = new PauseCampaignUseCase.Input(campaignID);

        ActivateCampaignUseCase activateUseCase = new ActivateCampaignUseCase(inMemoryCampaignRepository);
        ActivateCampaignUseCase.Input activateInput = new ActivateCampaignUseCase.Input(campaignID);

        // Act
        activateUseCase.execute(activateInput);
        PauseCampaignUseCase.Output output = useCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(CampaignStatusEnum.PAUSED, output.status());
    }

    @Test
    void shouldThrowErrorWhenCampaignIsNotFound() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);

        PauseCampaignUseCase useCase = new PauseCampaignUseCase(inMemoryCampaignRepository);
        PauseCampaignUseCase.Input input = new PauseCampaignUseCase.Input("123");

        // Assert
        assertThrows(NotFoundException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    void shouldThrowErrorWhenCampaignIsAlreadyPaused() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        final Campaign createdCampaign = inMemoryCampaignRepository.create(newCampaign);
        final String campaignID = createdCampaign.getCampaignID().value();

        ActivateCampaignUseCase activateUseCase = new ActivateCampaignUseCase(inMemoryCampaignRepository);
        ActivateCampaignUseCase.Input activateInput = new ActivateCampaignUseCase.Input(campaignID);

        PauseCampaignUseCase useCase = new PauseCampaignUseCase(inMemoryCampaignRepository);
        PauseCampaignUseCase.Input input = new PauseCampaignUseCase.Input(createdCampaign.getCampaignID().value());

        // Act
        activateUseCase.execute(activateInput);
        useCase.execute(input);

        // Assert
        assertThrows(DomainException.class, () -> {
            useCase.execute(input);
        });
    }
}
