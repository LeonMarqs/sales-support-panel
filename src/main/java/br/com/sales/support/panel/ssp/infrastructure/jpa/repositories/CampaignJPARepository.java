package br.com.sales.support.panel.ssp.infrastructure.jpa.repositories;

import br.com.sales.support.panel.ssp.infrastructure.jpa.entities.CampaignJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CampaignJPARepository extends JpaRepository<CampaignJPA, UUID>, JpaSpecificationExecutor<CampaignJPA> {

}
