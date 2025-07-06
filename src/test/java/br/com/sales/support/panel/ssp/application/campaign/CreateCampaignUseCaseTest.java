package br.com.sales.support.panel.ssp.application.campaign;

import br.com.sales.support.panel.ssp.domain.campaign.CampaignRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateCampaignUseCaseTest {

    @Test
    void shouldCreateCampaignSuccessfully() {
        // Arrange
        CampaignRepository inMemoryCampaignRepository = new InMemoryCampaignRepository();
        CreateCampaignUseCase createCampaignUseCase = new CreateCampaignUseCase(inMemoryCampaignRepository);
        CreateCampaignUseCase.Input input = new CreateCampaignUseCase.Input("Test Campaign", LocalDate.now(), null, null);

        // Act
        CreateCampaignUseCase.Output output = createCampaignUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertNotNull(output.id());
    }
}
