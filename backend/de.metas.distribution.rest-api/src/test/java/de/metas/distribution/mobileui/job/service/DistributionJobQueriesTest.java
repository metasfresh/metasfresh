package de.metas.distribution.mobileui.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link DistributionJobQueries#toActiveNotAssignedDDOrderQuery(DDOrderReferenceQuery)}:
 * <ul>
 *   <li>Y case — regular workplace: locatorToId present, excludeLocatorToIds absent → locatorToIds predicate matches exactly that locator, excludeLocatorToIds null</li>
 *   <li>N case — packing-place workplace: locatorToId absent, excludeLocatorToIds populated → locatorToIds is any-match, excludeLocatorToIds propagated</li>
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
				.warehouseToId(W1)
				.locatorToId(L1)
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
	void toActiveNotAssignedDDOrderQuery_packingPlaceWorkplace_excludeLocatorToIdsSet()
	{
		// Given: packing-place workplace — locatorToId absent, excludeLocatorToIds populated
		final ImmutableSet<LocatorId> packingLocators = ImmutableSet.of(L1, L2);
		final DDOrderReferenceQuery query = DDOrderReferenceQuery.builder()
				.responsibleId(UserId.ofRepoId(999))
				.warehouseToId(W1)
				.locatorToId(null)
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
}
