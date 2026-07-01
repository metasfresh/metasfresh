package de.metas.distribution.mobileui.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link DistributionJobQueries#toActiveNotAssignedDDOrderQuery(DDOrderReferenceQuery)}:
 * <ul>
 *   <li>Y case — packing-place workplace (IsPackingPlace=Y): locatorToId present, excludeLocatorToIds absent → locatorToIds predicate matches exactly that locator, excludeLocatorToIds null</li>
 *   <li>N case — replenishment workplace (IsPackingPlace=N): locatorToId absent, excludeLocatorToIds populated → locatorToIds is any-match, excludeLocatorToIds propagated</li>
 * </ul>
 */
class DistributionJobQueriesTest
{
	private static final WarehouseId W1 = WarehouseId.ofRepoId(1);
	private static final LocatorId L1 = LocatorId.ofRepoId(1, 101);
	private static final LocatorId L2 = LocatorId.ofRepoId(1, 102);

	@Test
	void toActiveNotAssignedDDOrderQuery_regularWorkplace_locatorToIdSet()
	{
		// Given: regular (non-packing) workplace — locatorToId set, excludeLocatorToIds absent
		final DDOrderReferenceQuery query = DDOrderReferenceQuery.builder()
				.responsibleId(UserId.ofRepoId(999))
				.workplaceWarehouseId(W1)
				.workplacePickFromLocatorId(L1)
				.excludeLocatorToIds(null)
				.build();

		// When
		final DDOrderQuery result = DistributionJobQueries.toActiveNotAssignedDDOrderQuery(query);

		// Then: locatorToIds matches exactly L1; excludeLocatorToIds is null/empty
		final InSetPredicate<LocatorId> locatorToIds = result.getLocatorToIds();
		assertThat(locatorToIds).isNotNull();
		assertThat(locatorToIds.isAny()).isFalse();
		assertThat(locatorToIds.test(L1)).isTrue();
		assertThat(locatorToIds.test(L2)).isFalse();

		@Nullable final Set<LocatorId> excludeLocatorToIds = result.getExcludeLocatorToIds();
		assertThat(excludeLocatorToIds).isNullOrEmpty();
	}

	@Test
	void toActiveNotAssignedDDOrderQuery_replenishmentWorkplace_excludeLocatorToIdsSet()
	{
		// Given: replenishment (non-packing, IsPackingPlace=N) workplace — locatorToId absent, excludeLocatorToIds populated
		final ImmutableSet<LocatorId> packingLocators = ImmutableSet.of(L1, L2);
		final DDOrderReferenceQuery query = DDOrderReferenceQuery.builder()
				.responsibleId(UserId.ofRepoId(999))
				.workplaceWarehouseId(W1)
				.workplacePickFromLocatorId(null)
				.excludeLocatorToIds(packingLocators)
				.build();

		// When
		final DDOrderQuery result = DistributionJobQueries.toActiveNotAssignedDDOrderQuery(query);

		// Then: locatorToIds is any-match (null or isAny()); excludeLocatorToIds == {L1, L2}
		final InSetPredicate<LocatorId> locatorToIds = result.getLocatorToIds();
		// locatorToId was null → InSetPredicate.onlyOrAny(null) → isAny()
		assertThat(locatorToIds == null || locatorToIds.isAny()).isTrue();

		final Set<LocatorId> excludeLocatorToIds = result.getExcludeLocatorToIds();
		assertThat(excludeLocatorToIds).containsExactlyInAnyOrderElementsOf(packingLocators);
	}

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
