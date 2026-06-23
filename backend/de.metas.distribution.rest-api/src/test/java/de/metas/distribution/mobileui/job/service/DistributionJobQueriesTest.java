package de.metas.distribution.mobileui.job.service;

import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DistributionJobQueriesTest
{
	@Test
	void workplaceWarehouse_mapsToFromOrTo_andNoLocatorFilter()
	{
		final WarehouseId workplaceWarehouseId = WarehouseId.ofRepoId(201);

		final DDOrderReferenceQuery referenceQuery = DDOrderReferenceQuery.builder()
				.responsibleId(UserId.ofRepoId(1000))
				.workplaceWarehouseId(workplaceWarehouseId)
				.build();

		final DDOrderQuery query = DistributionJobQueries.toActiveNotAssignedDDOrderQuery(referenceQuery);

		assertThat(query.getFromOrToWarehouseId()).isEqualTo(workplaceWarehouseId);
		assertThat(query.getLocatorToIds()).isNull();   // launcher no longer sets a locator visibility filter
		// no facet => match-anything (InSetPredicate.any()), not a narrowing filter
		assertThat(query.getWarehouseToIds()).isEqualTo(InSetPredicate.any());
	}
}
