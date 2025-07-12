package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.domain.campaign.*;
import br.com.sales.support.panel.ssp.domain.exceptions.DomainException;
import br.com.sales.support.panel.ssp.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class CompleteCampaignUseCaseTest {

    @Test
    void shouldCompleteCampaignSuccessfully() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        inMemoryCampaignRepository.create(newCampaign);

        final CampaignID campaignID = newCampaign.getCampaignID();

        CompleteCampaignUseCase useCase = new CompleteCampaignUseCase(inMemoryCampaignRepository);
        CompleteCampaignUseCase.Input input = new CompleteCampaignUseCase.Input(campaignID);

        ActivateCampaignUseCase activateUseCase = new ActivateCampaignUseCase(inMemoryCampaignRepository);
        ActivateCampaignUseCase.Input activateInput = new ActivateCampaignUseCase.Input(campaignID);

        // Act
        activateUseCase.execute(activateInput);
        CompleteCampaignUseCase.Output output = useCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(CampaignStatusEnum.COMPLETED, output.status());
        assertEquals(LocalDate.now(), output.endDate());
    }

    @Test
    void shouldThrowErrorWhenCampaignIsNotFound() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);

        CompleteCampaignUseCase useCase = new CompleteCampaignUseCase(inMemoryCampaignRepository);
        CompleteCampaignUseCase.Input input = new CompleteCampaignUseCase.Input(new CampaignID("123"));

        // Assert
        assertThrows(NotFoundException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    void shouldThrowErrorWhenCampaignIsAlreadyCompleted() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        final Campaign createdCampaign = inMemoryCampaignRepository.create(newCampaign);
        final CampaignID campaignID = createdCampaign.getCampaignID();

        ActivateCampaignUseCase activateUseCase = new ActivateCampaignUseCase(inMemoryCampaignRepository);
        ActivateCampaignUseCase.Input activateInput = new ActivateCampaignUseCase.Input(campaignID);

        CompleteCampaignUseCase useCase = new CompleteCampaignUseCase(inMemoryCampaignRepository);
        CompleteCampaignUseCase.Input input = new CompleteCampaignUseCase.Input(createdCampaign.getCampaignID());

        // Act
        activateUseCase.execute(activateInput);
        useCase.execute(input);

        // Assert
        assertThrows(DomainException.class, () -> {
            useCase.execute(input);
        });
    }
}
