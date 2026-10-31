/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.material.planning;

import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.organization.OrgId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the per-usage classification of a {@link ProductPlanning} row.
 * These predicates are the heart of the fix for <a href="https://github.com/metasfresh/me03/issues/28877">me03#28877</a>:
 * they determine which advisor(s) a given PP row feeds, and together with
 * {@code SupplyRequiredHandlerHelper.createContextsByUsage} they ensure that
 * multiple PP rows with different usages all get a chance to drive their advisor.
 */
class PlanningUsageTest
{
	private static ProductPlanning.ProductPlanningBuilder plainBuilder()
	{
		return ProductPlanning.builder().orgId(OrgId.ANY);
	}

	@Test
	void manufacturingRow_matchesOnlyManufacturing()
	{
		final ProductPlanning pp = plainBuilder().isManufactured(true).build();

		assertThat(PlanningUsage.MANUFACTURING.matches(pp)).isTrue();
		assertThat(PlanningUsage.DISTRIBUTION.matches(pp)).isFalse();
		assertThat(PlanningUsage.PURCHASING.matches(pp)).isFalse();
	}

	@Test
	void distributionRow_matchesOnlyDistribution()
	{
		final ProductPlanning pp = plainBuilder().distributionNetworkId(DistributionNetworkId.ofRepoId(42)).build();

		assertThat(PlanningUsage.MANUFACTURING.matches(pp)).isFalse();
		assertThat(PlanningUsage.DISTRIBUTION.matches(pp)).isTrue();
		assertThat(PlanningUsage.PURCHASING.matches(pp)).isFalse();
	}

	@Test
	void purchasingRow_matchesOnlyPurchasing()
	{
		final ProductPlanning pp = plainBuilder().isPurchased(true).build();

		assertThat(PlanningUsage.MANUFACTURING.matches(pp)).isFalse();
		assertThat(PlanningUsage.DISTRIBUTION.matches(pp)).isFalse();
		assertThat(PlanningUsage.PURCHASING.matches(pp)).isTrue();
	}

	/**
	 * Preserves the pre-fix behavior: a single row that declares itself manufactured,
	 * distributed, and purchased feeds all three advisors.
	 */
	@Test
	void rowWithAllThreeFlags_matchesAllThreeUsages()
	{
		final ProductPlanning pp = plainBuilder()
				.isManufactured(true)
				.distributionNetworkId(DistributionNetworkId.ofRepoId(42))
				.isPurchased(true)
				.build();

		assertThat(PlanningUsage.MANUFACTURING.matches(pp)).isTrue();
		assertThat(PlanningUsage.DISTRIBUTION.matches(pp)).isTrue();
		assertThat(PlanningUsage.PURCHASING.matches(pp)).isTrue();
	}

	/**
	 * Picking orders are a separate beast and must NOT drive {@code PPOrderCandidateAdvisedEventCreator}
	 * even when {@code isManufactured=Y}. This mirrors the guard that used to live in
	 * {@code PPOrderCandidateDemandMatcher}.
	 */
	@Test
	void manufacturedPickingOrderRow_doesNotMatchManufacturing()
	{
		final ProductPlanning pp = plainBuilder()
				.isManufactured(true)
				.isPickingOrder(true)
				.build();

		assertThat(PlanningUsage.MANUFACTURING.matches(pp)).isFalse();
	}

	@Test
	void rowWithNoFlags_matchesNothing()
	{
		final ProductPlanning pp = plainBuilder().build();

		assertThat(PlanningUsage.MANUFACTURING.matches(pp)).isFalse();
		assertThat(PlanningUsage.DISTRIBUTION.matches(pp)).isFalse();
		assertThat(PlanningUsage.PURCHASING.matches(pp)).isFalse();
	}
}
