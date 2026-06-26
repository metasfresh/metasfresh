package de.metas.distribution.mobileui.job.service;

import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DistributionJobQueriesTest
{
	@Nested
	class Workplace
	{
		@Test
		void mapsWarehouseAndPickFromLocator()
		{
			final WarehouseId workplaceWarehouseId = WarehouseId.ofRepoId(201);
			final LocatorId workplacePickFromLocatorId = LocatorId.ofRepoId(201, 301);

			final DDOrderReferenceQuery referenceQuery = DDOrderReferenceQuery.builder()
					.responsibleId(UserId.ofRepoId(1000))
					.workplaceWarehouseId(workplaceWarehouseId)
					.workplacePickFromLocatorId(workplacePickFromLocatorId)
					.build();

			final DDOrderQuery query = DistributionJobQueries.toActiveNotAssignedDDOrderQuery(referenceQuery);

			// the workplace warehouse + pick-from locator drive the from-or-to visibility predicate
			assertThat(query.getWorkplaceWarehouseId()).isEqualTo(workplaceWarehouseId);
			assertThat(query.getWorkplacePickFromLocatorId()).isEqualTo(workplacePickFromLocatorId);
			// no facet => match-anything (InSetPredicate.any()), not a narrowing filter
			assertThat(query.getWarehouseToIds()).isEqualTo(InSetPredicate.any());
		}

		@Test
		void withoutPickFromLocator_leavesLocatorNull()
		{
			final WarehouseId workplaceWarehouseId = WarehouseId.ofRepoId(201);

			final DDOrderReferenceQuery referenceQuery = DDOrderReferenceQuery.builder()
					.responsibleId(UserId.ofRepoId(1000))
					.workplaceWarehouseId(workplaceWarehouseId)
					.build();

			final DDOrderQuery query = DistributionJobQueries.toActiveNotAssignedDDOrderQuery(referenceQuery);

			assertThat(query.getWorkplaceWarehouseId()).isEqualTo(workplaceWarehouseId);
			assertThat(query.getWorkplacePickFromLocatorId()).isNull();
		}
	}
}
