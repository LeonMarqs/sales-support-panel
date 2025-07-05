package br.com.sales.support.panel.ssp.infrastructure.jpa.repositories.specifications;

import br.com.sales.support.panel.ssp.domain.campaign.CampaignFilter;
import br.com.sales.support.panel.ssp.infrastructure.jpa.entities.CampaignJPA;
import br.com.sales.support.panel.ssp.infrastructure.jpa.entities.CampaignJPA_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CampaignJPASpecification {

    public static Specification<CampaignJPA> byFilter(final CampaignFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null) {
                return criteriaBuilder.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (filter.hasName()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(CampaignJPA_.NAME)), "%" + filter.name().toUpperCase() + "%"));
            }

            if (filter.hasBudget()) {
                predicates.add(criteriaBuilder.ge(root.get(CampaignJPA_.BUDGET), filter.budget()));
            }

            if (filter.hasStartDate()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(CampaignJPA_.START_DATE), filter.startDate()));
            }

            if (filter.hasEndDate()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(CampaignJPA_.END_DATE), filter.endDate()));
            }

            if (filter.hasStatus()) {
                predicates.add(criteriaBuilder.equal(root.get(CampaignJPA_.STATUS), filter.status()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
