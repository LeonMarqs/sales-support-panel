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


class CancelCampaignUseCaseTest {

    @Test
    void shouldCancelCampaignSuccessfully() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        inMemoryCampaignRepository.create(newCampaign);

        final String campaignID = newCampaign.getCampaignID().value();

        CancelCampaignUseCase useCase = new CancelCampaignUseCase(inMemoryCampaignRepository);
        CancelCampaignUseCase.Input input = new CancelCampaignUseCase.Input(campaignID);

        // Act
        CancelCampaignUseCase.Output output = useCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(CampaignStatusEnum.CANCELLED, output.status());
    }

    @Test
    void shouldThrowErrorWhenCampaignIsNotFound() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        inMemoryCampaignRepository.create(newCampaign);

        CancelCampaignUseCase useCase = new CancelCampaignUseCase(inMemoryCampaignRepository);
        CancelCampaignUseCase.Input input = new CancelCampaignUseCase.Input("123");

        // Assert
        assertThrows(NotFoundException.class, () -> {
            useCase.execute(input);
        });
    }

    @Test
    void shouldThrowErrorWhenCampaignIsAlreadyCancelled() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        Campaign newCampaign = Campaign.newCampaign("Test Campaign", BigDecimal.valueOf(1000.00), LocalDate.now(), CampaignPlatformEnum.FACEBOOK_ADS);
        final Campaign createdCampaign = inMemoryCampaignRepository.create(newCampaign);

        CancelCampaignUseCase useCase = new CancelCampaignUseCase(inMemoryCampaignRepository);
        CancelCampaignUseCase.Input input = new CancelCampaignUseCase.Input(createdCampaign.getCampaignID().value());
        useCase.execute(input);

        // Assert
        assertThrows(DomainException.class, () -> {
            useCase.execute(input);
        });
    }
}
